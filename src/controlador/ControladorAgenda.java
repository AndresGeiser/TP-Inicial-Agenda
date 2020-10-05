package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import modelo.Agenda;
import modelo.Regiones;
import persistencia.DAOSQLFactory;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.VentanaTipoContacto;
import vista.VentanaAgenda;
import vista.VentanaDetalles;
import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoDTO;

public class ControladorAgenda implements ActionListener
{
		private VentanaAgenda ventanaAgenda;
		private VentanaPersona ventanaPersona;
		private VentanaDetalles ventanaDetalles;
		private VentanaTipoContacto ventanaTipoContacto;
		
		private Agenda agenda;
		private Regiones regiones;
		
		private List<TipoDTO> tipos;
		
		private List<PaisDTO> paises;
		private List<ProvinciaDTO> provincias;
		private List<LocalidadDTO> localidades;
		
		private List<PersonaDTO> personasEnLista;
		private PersonaDTO personaSeleccionada;
		
		
		public ControladorAgenda(VentanaAgenda vista, Agenda agenda)
		{
			this.ventanaAgenda = vista;
			this.ventanaAgenda.getBtnAgregar().addActionListener(a -> ventanaAgregarPersona(a));
			this.ventanaAgenda.getBtnBorrar().addActionListener(a -> borrarPersonas(a));
			this.ventanaAgenda.getBtnReporte().addActionListener(a -> mostrarReporte(a));
			this.ventanaAgenda.getBtnEditar().addActionListener(a -> ventanaEditarPersona(a));
						
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnActualizarPersona().addActionListener(a -> editarPersona(a));
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(a -> agregarPersona(a));
			this.ventanaPersona.getCbxPais().addActionListener(a -> actualizarProvincias(a));
			this.ventanaPersona.getCbxProvincia().addActionListener(a -> actualizarLocalidades(a));
			this.ventanaPersona.getChckDomicilio().addActionListener(a -> habilitarIngresoDeDomicilio(a));
			this.ventanaPersona.getBtnAgregarTipo().addActionListener(a -> ventanaTipoContacto(a));
			this.ventanaPersona.getBtnEditarTipo().addActionListener(a -> ventanaTipoContacto(a));
			this.ventanaPersona.getBtnBorrarTipo().addActionListener(a -> ventanaTipoContacto(a));
			
			this.ventanaTipoContacto = VentanaTipoContacto.getInstance(ventanaPersona, true);
			this.ventanaTipoContacto.getBtnAgregar().addActionListener(a -> agregarNuevoTipo(a));
			this.ventanaTipoContacto.getBtnEditar().addActionListener(a -> editarTipo(a));
			this.ventanaTipoContacto.getBtnBorrar().addActionListener(a -> borrarTipo(a));
			this.ventanaTipoContacto.getBtnCancelar().addActionListener(a -> ventanaTipoContacto(a));
			
			this.ventanaDetalles = VentanaDetalles.getInstance(ventanaAgenda, true);
	
			this.regiones = new Regiones(new DAOSQLFactory());
			this.paises = this.regiones.obtenerPaises();
			
			this.agenda = agenda;
			this.tipos = agenda.obtenerTiposDeContacto();
		}
		
		public void inicializar()
		{
			this.ventanaPersona.cargarPaises(paises);
			this.ventanaPersona.cargarTiposDeContacto(tipos);
			
			refrescarLista();
			ventanaAgenda.mostrar();
		}


		/**
		 * Muestra la ventana para agregar un nuevo contacto
		 */
		private void ventanaAgregarPersona(ActionEvent a) 
		{
			ventanaPersona.mostrarVentana();
		}

		/**
		 * Muestra la ventana para editar un contacto
		 */
		public void ventanaEditarPersona(ActionEvent s)
		{
			Contacto[] contactos = ventanaAgenda.getContactos();
			
			for (int i = 0; i < contactos.length; i++) {
				if(contactos[i].estaSeleccionado()) {
					personaSeleccionada = personasEnLista.get(i);
					break;
				}
			}
			
			if(personaSeleccionada == null) 
				JOptionPane.showMessageDialog(null, "Seleccione el contacto que desea editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			else 
				ventanaPersona.mostrarVentanaConDatos(personaSeleccionada);
			
			this.refrescarLista();
		}
		
		/**
		 * Muestra la ventana con un reporte generado
		 */
		private void mostrarReporte(ActionEvent r) 
		{
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();
		}
		
		/**
		 * Muestra la ventana de detalles del contacto
		 */
		private void mostrarDetalles(ActionEvent b) 
		{
			for (int i = 0; i < ventanaAgenda.getContactos().length; i++) 
			{
				Contacto contacto = ventanaAgenda.getContactos()[i];
				JButton boton = contacto.getBtnVerMas();
				
				if(boton == b.getSource()) 
				{
					ventanaDetalles.cargar(personasEnLista.get(i));
					ventanaDetalles.mostrar();
					break;
				}
			}
		}
		

		private void agregarPersona(ActionEvent p) {

			if(validarDatos()) 
			{
				String nombre = ventanaPersona.getTxtNombre().getText();
				String tel = ventanaPersona.getTxtTelefono().getText();
				String correo = ventanaPersona.getTxtCorreo().getText();
				String cumple = ventanaPersona.getTxtCumple().getText();
				
				int indexTipo = ventanaPersona.getTipo().getSelectedIndex();
				TipoDTO tipo = (indexTipo == -1) ? null : tipos.get(indexTipo - 1);
				
				String pais, provincia, localidad, calle, altura, tipoDomicilio, piso, dpto;
				pais = provincia = localidad = calle = altura = tipoDomicilio = piso = dpto = "";
				
				boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
				if(agregoDomicilio) 
				{
					pais = ventanaPersona.getCbxPais().getSelectedItem().toString();
					provincia = ventanaPersona.getCbxProvincia().getSelectedItem().toString();
					localidad = ventanaPersona.getCbxLocalidad().getSelectedItem().toString();
					calle = ventanaPersona.getTxtCalle().getText();
					altura = ventanaPersona.getTxtAltura().getText();
					tipoDomicilio = ventanaPersona.getCbxTipoDomicilio().getSelectedItem().toString();
					piso = ventanaPersona.getTxtPiso().getText();
					dpto = ventanaPersona.getTxtDpto().getText();
				} 
				
				
				DomicilioDTO domicilio  = new DomicilioDTO(pais, provincia, localidad, calle, altura, tipoDomicilio, piso, dpto);
				
				PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel, correo, tipo, cumple, domicilio);
				agenda.agregarPersona(nuevaPersona);
				refrescarLista();
				ventanaPersona.cerrar();	
			}
		}
		
		public void editarPersona(ActionEvent e)
		{
			if(validarDatos()) 
			{
				String nombre = ventanaPersona.getTxtNombre().getText();
				String tel = ventanaPersona.getTxtTelefono().getText();
				String correo = ventanaPersona.getTxtCorreo().getText();
				String cumple = ventanaPersona.getTxtCumple().getText();
				
				int indexTipo = ventanaPersona.getTipo().getSelectedIndex();
				TipoDTO tipo = (indexTipo == -1) ? null : tipos.get(indexTipo - 1);
				
				personaSeleccionada.setNombre(nombre);
				personaSeleccionada.setCorreo(correo);
				personaSeleccionada.setTelefono(tel);
				personaSeleccionada.setTipo_contacto(tipo);
				personaSeleccionada.setFecha_cumple(cumple);
				
				String pais, provincia, localidad, calle, altura, tipoDomicilio, piso, dpto;
				pais = provincia = localidad = calle = altura = tipoDomicilio = piso = dpto = "";
				boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
				if(agregoDomicilio) 
				{
					pais = ventanaPersona.getCbxPais().getSelectedItem().toString();
					provincia = ventanaPersona.getCbxProvincia().getSelectedItem().toString();
					localidad = ventanaPersona.getCbxLocalidad().getSelectedItem().toString();
					calle = ventanaPersona.getTxtCalle().getText();
					altura = ventanaPersona.getTxtAltura().getText();
					tipoDomicilio = ventanaPersona.getCbxTipoDomicilio().getSelectedItem().toString();
					piso = ventanaPersona.getTxtPiso().getText();
					dpto = ventanaPersona.getTxtDpto().getText();
				}
				
				DomicilioDTO domicilio  = new DomicilioDTO(pais, provincia, localidad, calle, altura, tipoDomicilio, piso, dpto);
				
				personaSeleccionada.setDomicilio(domicilio);

				agenda.editarPersona(personaSeleccionada);
				refrescarLista();
				ventanaPersona.cerrar();
				personaSeleccionada = null;
			}
		}

		public void borrarPersonas(ActionEvent s)
		{
			Contacto[] contactos = ventanaAgenda.getContactos();
			
			for (int i = 0; i < contactos.length; i++) 
			{
				if(contactos[i].estaSeleccionado()) 
					agenda.borrarPersona(personasEnLista.get(i));
			}
			
			refrescarLista();
		}
		
		
		private boolean validarDatos() 
		{
			String nombre = ventanaPersona.getTxtNombre().getText().trim();
			String tel = ventanaPersona.getTxtTelefono().getText().trim();
			String correo = ventanaPersona.getTxtCorreo().getText().trim();
			
			
			String mensajeAdvertencia = "";
			int numMensaje = 1;
			
			if(nombre.equals("") || tel.equals("")) 
				mensajeAdvertencia += numMensaje++ + ") El nombre y telefono del contacto es requerido.\n";
			
			if(nombre.length() > 20)
				mensajeAdvertencia += numMensaje++ + ") Nombre de contacto muy largo. (Maximo 20 caracteres)\\n";
			else 
			{
				for (PersonaDTO personaDTO : personasEnLista) 
				{
					if(personaDTO.getNombre().equals(nombre) && personaDTO.getIdPersona() != personaSeleccionada.getIdPersona()) 
					{
						mensajeAdvertencia += numMensaje++ + ") Ya existe un contacto con el nombre: '" + nombre + "'. \n";
						return false;
					}
				}
			}
			
			if(!correo.equals("")) 
			{
				String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
				Pattern pattern = Pattern.compile(emailPattern);
				Matcher matcher = pattern.matcher(correo);
				if (!matcher.matches()) 
					mensajeAdvertencia += numMensaje++ + ") Verifique que el correo este bien escrito.\n";
			}
			
			boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
			if(agregoDomicilio) 
			{
				String calle = ventanaPersona.getTxtCalle().getText().trim();
				String altura = ventanaPersona.getTxtAltura().getText().trim();
				
				if(calle.equals("") || altura.equals("")) 
					mensajeAdvertencia += numMensaje++ + ") La calle y altura del domicilio no puede quedar en blanco.\n";	
			}
			
			
			if(!mensajeAdvertencia.equals("")) 
				JOptionPane.showMessageDialog(null, "Se encontraron los siguientes errores en los campos:\n\n" + mensajeAdvertencia, "Aviso", JOptionPane.WARNING_MESSAGE); 
	
			return (mensajeAdvertencia.equals("")) ? true : false;
		}
		
		
		private void refrescarLista()
		{
			personasEnLista = agenda.obtenerPersonas();
			ventanaAgenda.llenarLista(personasEnLista);
			
			//Agregamos la funcion para ver los detalles del contacto a cada boton
			for (Contacto contacto : ventanaAgenda.getContactos())
				contacto.getBtnVerMas().addActionListener(t->mostrarDetalles(t));
		}

		private void actualizarProvincias(ActionEvent p) 
		{
			int i = ventanaPersona.getCbxPais().getSelectedIndex();
			PaisDTO pais = paises.get(i);
			provincias = regiones.obtenerProvincias(pais);
			ventanaPersona.cargarProvincias(provincias);
		}
		
		private void actualizarLocalidades(ActionEvent p) 
		{	
			int i = ventanaPersona.getCbxProvincia().getSelectedIndex();
			if(i != -1) //para que no tire error de indice al seleccionar un pais
			{
				ProvinciaDTO provincia = provincias.get(i);
				localidades = regiones.obtenerLocalidades(provincia);
				ventanaPersona.cargarLocalidades(localidades);
			}
		}
		
		private void habilitarIngresoDeDomicilio(ActionEvent p) 
		{
			if(ventanaPersona.getChckDomicilio().isSelected())
				ventanaPersona.habilitarCamposDomicilio(true);
			else
				ventanaPersona.habilitarCamposDomicilio(false);
		}

		
		private void ventanaTipoContacto(ActionEvent p) 
		{
			JButton btnAgregar = ventanaPersona.getBtnAgregarTipo();
			JButton btnEditar = ventanaPersona.getBtnEditarTipo();
			JButton btnBorrar = ventanaPersona.getBtnBorrarTipo();
			JButton btnCancelar = ventanaTipoContacto.getBtnCancelar();
			
			if(p.getSource() == btnAgregar)
				ventanaTipoContacto.mostrarAgregar();
			else if(p.getSource() == btnEditar)
				ventanaTipoContacto.mostrarEditar(tipos);
			else if(p.getSource() == btnBorrar)
				ventanaTipoContacto.mostrarBorrar(tipos);
			else if(p.getSource() == btnCancelar)
				ventanaTipoContacto.cerrar();
		}
	
		
		public void agregarNuevoTipo(ActionEvent p) 
		{
			String nombre = ventanaTipoContacto.getTxtNombreAgregar().getText().trim();
			
			if(!nombre.equals("")) 
			{
				nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1); //Convertimos la primera letra en mayuscula
				
				boolean existe = false;
				for (TipoDTO tipo : tipos) {
					if(tipo.getNombre().equals(nombre))
						existe = true;
				}
				
				if(existe)
					JOptionPane.showMessageDialog(null, "Ya existe un tipo de contacto con el nombre '" + nombre + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
				else 
				{
					agenda.agregarTipoDeContacto(new TipoDTO(0, nombre));
					ventanaTipoContacto.cerrar();
					tipos = agenda.obtenerTiposDeContacto();
					ventanaPersona.cargarTiposDeContacto(tipos);
					JOptionPane.showMessageDialog(null, "Nuevo tipo de contacto guardado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
			else
				JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			
		}
		
		public void editarTipo(ActionEvent p) 
		{
			int seleccionado = ventanaTipoContacto.getCbxTiposEditar().getSelectedIndex();
			String nombreNuevo = ventanaTipoContacto.getTxtNombreEditar().getText().trim();
			
			if(!nombreNuevo.equals("")) 
			{
				nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1); //Convertimos la primera letra en mayuscula
				
				boolean existe = false;
				for (TipoDTO tipo : tipos) {
					if(tipo.getNombre().equals(nombreNuevo))
						existe = true;
				}
				
				if(existe)
					JOptionPane.showMessageDialog(null, "Ya existe un tipo de contacto con el nombre '" + nombreNuevo + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
				else 
				{
					TipoDTO tipo = tipos.get(seleccionado);
					String nombreViejo = tipo.getNombre();
					
					int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan el tipo '" + nombreViejo + "' seran afectados.\n¿Estas seguro de cambiar el tipo '" + nombreViejo + "' por '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
					
					if(respuesta == 0) 
					{
						tipo.setNombre(nombreNuevo);
						agenda.editarTipoDeContacto(tipo);
						ventanaTipoContacto.cerrar();
						tipos = agenda.obtenerTiposDeContacto();
						ventanaPersona.cargarTiposDeContacto(tipos);
						refrescarLista();
						JOptionPane.showMessageDialog(null, "Tipo de contacto actualizado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
					}
				}
			}
			else
				JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
		
		public void borrarTipo(ActionEvent p) 
		{
			JCheckBox[] chksTipos = ventanaTipoContacto.getChksPanelBorrar();
			
			//Recorremos para verificar si selecciono algun tipo 
			boolean selecciono = false;
			for (int i = 0; i < chksTipos.length; i++) 
				if (chksTipos[i].isSelected()) 
					selecciono = true;
			
			if (selecciono) 
			{
				
				int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan algunos de los tipos seleccionados seran afectados.\n¿Esta seguro de eliminarlos?", "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
				
				if(respuesta == 0) 
				{
					//Recorremos y eliminamos los tipos seleccionados
					for (int i = 0; i < chksTipos.length; i++) 
					{
						if (chksTipos[i].isSelected()) 
						{
							TipoDTO tipo = tipos.get(i);
							agenda.borrarTipoDeContacto(tipo);
						}
					}
					
					ventanaTipoContacto.cerrar();
					tipos = agenda.obtenerTiposDeContacto();
					ventanaPersona.cargarTiposDeContacto(tipos);
					refrescarLista();
					JOptionPane.showMessageDialog(null, "Tipos de contacto eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
				
			}
			else
				JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguno.", "Aviso", JOptionPane.WARNING_MESSAGE);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
