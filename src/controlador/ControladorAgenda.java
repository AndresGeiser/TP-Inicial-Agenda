package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.Agenda;
import modelo.Regiones;
import persistencia.DAOSQLFactory;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.VentanaAgenda;
import vista.VentanaDetalles;
import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;

public class ControladorAgenda implements ActionListener
{
		private VentanaAgenda ventanaAgenda;
		private VentanaPersona ventanaPersona;
		private VentanaDetalles ventanaDetalles;
		private Agenda agenda;
		private Regiones regiones;
		
		private List<PersonaDTO> personasEnLista;
		private PersonaDTO personaSeleccionada;
		
		private List<PaisDTO> paises;
		private List<ProvinciaDTO> provincias;
		private List<LocalidadDTO> localidades;
		
		public ControladorAgenda(VentanaAgenda vista, Agenda agenda)
		{
			this.ventanaAgenda = vista;
			this.ventanaAgenda.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.ventanaAgenda.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.ventanaAgenda.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.ventanaAgenda.getBtnEditar().addActionListener(t->ventanaEditarPersona(t));
						
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnActualizarPersona().addActionListener(u-> editarPersona(u));
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaPersona.getCbxPais().addActionListener(p->actualizarProvincias(p));
			this.ventanaPersona.getCbxProvincia().addActionListener(p->actualizarLocalidades(p));
			this.ventanaPersona.getChckDomicilio().addActionListener(p->habilitarIngresoDeDomicilio(p));
			
			this.ventanaDetalles = VentanaDetalles.getInstance(ventanaAgenda, true);
	
			//Carga de paises
			this.regiones = new Regiones(new DAOSQLFactory());
			this.paises = this.regiones.obtenerPaises();
			this.ventanaPersona.cargarPaises(this.paises);
			
			this.agenda = agenda;
		}
		
		public void inicializar()
		{
			refrescarLista();
			ventanaAgenda.mostrar();
		}


		/*
		 * Muestra la ventana para agregar un nuevo contacto
		 */
		private void ventanaAgregarPersona(ActionEvent a) 
		{
			ventanaPersona.mostrarVentana();
		}

		/*
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
		
		private void guardarPersona(ActionEvent p) {

			if(validarDatos()) 
			{
				String nombre = ventanaPersona.getTxtNombre().getText();
				String tel = ventanaPersona.getTxtTelefono().getText();
				String correo = ventanaPersona.getTxtCorreo().getText();
				String tipo = ventanaPersona.getTipo().getSelectedItem().toString();
				String cumple = ventanaPersona.getTxtCumple().getText();
				
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
				
				System.out.println(pais);
				System.out.println(tipoDomicilio);
				
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
				String tipo = ventanaPersona.getTipo().getSelectedItem().toString();
				String cumple = ventanaPersona.getTxtCumple().getText();
				
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

		public void borrarPersona(ActionEvent s)
		{
			Contacto[] contactos = ventanaAgenda.getContactos();
			
			for (int i = 0; i < contactos.length; i++) 
			{
				if(contactos[i].estaSeleccionado()) 
					agenda.borrarPersona(personasEnLista.get(i));
			}
			
			refrescarLista();
		}
		
		
		private void mostrarReporte(ActionEvent r) 
		{
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();	
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
		
		/*
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

		
		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
