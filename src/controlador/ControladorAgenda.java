package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import modelo.Agenda;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.VentanaTipoContacto;
import vista.VentanaUbicaciones;
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
	private static ControladorAgenda INSTANCE;
	
	private VentanaAgenda ventanaAgenda;
	private VentanaPersona ventanaPersona;
	private VentanaDetalles ventanaDetalles;
	private VentanaTipoContacto ventanaTipoContacto;
	
	private ControladorUbicaciones controladorUbicaciones;
	
	private Agenda agenda;
	
	private List<TipoDTO> tipos;
	
	private List<PaisDTO> paises;
	private List<ProvinciaDTO> provincias;
	private List<LocalidadDTO> localidades;
	
	private List<PersonaDTO> personasEnLista;
	private PersonaDTO personaSeleccionada;

	
	public static ControladorAgenda getInstance(VentanaAgenda vista, Agenda modelo)
	{
		if(INSTANCE == null)
			INSTANCE = new ControladorAgenda(vista, modelo);
		
		return INSTANCE;
	}
	
	private ControladorAgenda(VentanaAgenda vista, Agenda agenda)
	{
		this.ventanaAgenda = vista;
		this.ventanaAgenda.getBtnAgregar().addActionListener(a -> ventanaAgregarPersona(a));
		this.ventanaAgenda.getBtnBorrar().addActionListener(a -> borrarContactos(a));
		this.ventanaAgenda.getBtnReporte().addActionListener(a -> mostrarReporte(a));
		this.ventanaAgenda.getBtnEditar().addActionListener(a -> ventanaEditarPersona(a));
					
		this.ventanaPersona = VentanaPersona.getInstance();
		this.ventanaPersona.getBtnActualizarPersona().addActionListener(a -> editarPersona(a));
		this.ventanaPersona.getBtnAgregarPersona().addActionListener(a -> agregarPersona(a));
		this.ventanaPersona.getCbxPais().addActionListener(a -> actualizarProvincias(a));
		this.ventanaPersona.getCbxProvincia().addActionListener(a -> actualizarLocalidades(a));
		this.ventanaPersona.getChckDomicilio().addActionListener(a -> habilitarIngresoDeDomicilio(a));
		this.ventanaPersona.getBtnConfigurarTipo().addActionListener(a -> ventanaTipoContacto(a));
		this.ventanaPersona.getBtnConfigurarUbicaciones().addActionListener(a -> ventanaUbicaciones(a));
		
		this.ventanaTipoContacto = VentanaTipoContacto.getInstance(ventanaPersona, true);
		this.ventanaTipoContacto.getBtnAgregar().addActionListener(a -> agregarNuevoTipo(a));
		this.ventanaTipoContacto.getBtnEditar().addActionListener(a -> editarTipo(a));
		this.ventanaTipoContacto.getBtnBorrar().addActionListener(a -> borrarTipo(a));
		
		this.ventanaDetalles = VentanaDetalles.getInstance(ventanaAgenda, true);

		VentanaUbicaciones ventanaUbicaciones = VentanaUbicaciones.getInstance(ventanaPersona, true);
		this.controladorUbicaciones = ControladorUbicaciones.getInstance(ventanaUbicaciones, agenda);
		
		
		this.agenda = agenda;
		this.tipos = agenda.obtenerTiposDeContacto();
	}
	
	public void inicializar()
	{
		actualizarPaisesVentanaPersona();
		
		this.ventanaPersona.cargarTiposDeContacto(tipos);
		
		this.ventanaTipoContacto.cargarTipos(tipos);
		
		refrescarLista();
		ventanaAgenda.mostrar();
	}


	/**
	 * Muestra la ventana para agregar un nuevo contacto
	 */
	private void ventanaAgregarPersona(ActionEvent ae) 
	{
		ventanaPersona.mostrarVentana();
	}
	
	private void agregarPersona(ActionEvent ae) 
	{
		if (validarDatos()) 
		{
			String nombre = ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String correo = ventanaPersona.getTxtCorreo().getText();
			String cumple = ventanaPersona.getTxtCumple().getText();
			
			int indexTipo = ventanaPersona.getTipo().getSelectedIndex();
			TipoDTO tipo = (indexTipo == -1) ? null : tipos.get(indexTipo - 1);
			
			
			PaisDTO pais = null; ProvinciaDTO provincia = null; LocalidadDTO localidad = null;
			String calle, altura, tipoDomicilio, piso, dpto;
			calle = altura = tipoDomicilio = piso = dpto = "";
			boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
			if (agregoDomicilio) 
			{
				int indexPais = ventanaPersona.getCbxPais().getSelectedIndex();
				pais = paises.get(indexPais);
				
				int indexProv = ventanaPersona.getCbxProvincia().getSelectedIndex();
				provincia = provincias.get(indexProv);
				
				int indexLoc = ventanaPersona.getCbxLocalidad().getSelectedIndex();
				localidad = localidades.get(indexLoc);
				
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

	/**
	 * Muestra la ventana para editar un contacto
	 */
	public void ventanaEditarPersona(ActionEvent ae)
	{
		List<Contacto> contactos = ventanaAgenda.getContactos();
		
		for (int i = 0; i < contactos.size(); i++) 
		{
			if (contactos.get(i).estaSeleccionado()) 
			{
				personaSeleccionada = personasEnLista.get(i);
				break;
			}
		}
		
		if (personaSeleccionada == null) 
			JOptionPane.showMessageDialog(null, "Seleccione el contacto que desea editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
		else 
			ventanaPersona.mostrarVentanaConDatos(personaSeleccionada);
		
		this.refrescarLista();
	}
	
	public void editarPersona(ActionEvent ae)
	{
		if (validarDatos()) 
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
			
			PaisDTO pais = null; ProvinciaDTO provincia = null; LocalidadDTO localidad = null;
			String calle, altura, tipoDomicilio, piso, dpto;
			calle = altura = tipoDomicilio = piso = dpto = "";
			boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
			if (agregoDomicilio) 
			{
				int indexPais = ventanaPersona.getCbxPais().getSelectedIndex();
				pais = paises.get(indexPais);
				
				int indexProv = ventanaPersona.getCbxProvincia().getSelectedIndex();
				provincia = provincias.get(indexProv);
				
				int indexLoc = ventanaPersona.getCbxLocalidad().getSelectedIndex();
				localidad = localidades.get(indexLoc);
				
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
	
	/**
	 * Muestra la ventana con un reporte generado
	 */
	private void mostrarReporte(ActionEvent ae) 
	{
		ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
		reporte.mostrar();
	}
	

	/**
	 * Borra los contactos seleccionados
	 */
	public void borrarContactos(ActionEvent ae)
	{
		List<Contacto> contactos = ventanaAgenda.getContactos();
	
		boolean selecciono = false;
		for (int i = 0; i < contactos.size(); i++)
		{
			if (contactos.get(i).estaSeleccionado()) 
			{
				selecciono = true;
				break;
			}
		}
		
		if (selecciono) 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar los contactos seleccionados?", "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
			
			if (respuesta == 0) 
			{
				for (int i = 0; i < contactos.size(); i++) 
					if (contactos.get(i).estaSeleccionado()) 
						agenda.borrarPersona(personasEnLista.get(i));
				
				refrescarLista();
			}
		}
		else
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun contacto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			
	}
	
	/**
	 * Retorna true si los campos de la ventana de agregar o editar un contacto estan correctos o false en caso contrario
	 */
	private boolean validarDatos() 
	{	
		String mensajeAdvertencia = "";
		int numMensaje = 1;
		
		String nombre = ventanaPersona.getTxtNombre().getText().trim();
		String tel = ventanaPersona.getTxtTelefono().getText().trim();
		String correo = ventanaPersona.getTxtCorreo().getText().trim();
		
		//Validacion nombre
		if (nombre.equals("") || tel.equals("")) 
			mensajeAdvertencia += numMensaje++ + ") El nombre y telefono del contacto es requerido.\n";
		
		if (nombre.length() > 20)
			mensajeAdvertencia += numMensaje++ + ") Nombre de contacto muy largo. (Maximo 20 caracteres)\\n";
		else 
		{
			for (PersonaDTO personaDTO : personasEnLista) 
			{
				if (personaDTO.getNombre().equals(nombre) && personaDTO.getIdPersona() != personaSeleccionada.getIdPersona()) 
				{
					mensajeAdvertencia += numMensaje++ + ") Ya existe un contacto con el nombre: '" + nombre + "'. \n";
					return false;
				}
			}
		}
		
		//Validacion correo
		if (!correo.equals("")) 
		{
			String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
			Pattern pattern = Pattern.compile(emailPattern);
			Matcher matcher = pattern.matcher(correo);
			if (!matcher.matches()) 
				mensajeAdvertencia += numMensaje++ + ") Verifique que el correo este bien escrito.\n";
		}
		
		//Validacion Domicilio
		boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
		if (agregoDomicilio) 
		{
			String calle = ventanaPersona.getTxtCalle().getText().trim();
			String altura = ventanaPersona.getTxtAltura().getText().trim();
			
			if (calle.equals("") || altura.equals("")) 
				mensajeAdvertencia += numMensaje++ + ") La calle y altura del domicilio no puede quedar en blanco.\n";	
		}
		
		
		if (!mensajeAdvertencia.equals("")) 
			JOptionPane.showMessageDialog(null, "Se encontraron los siguientes errores en los campos:\n\n" + mensajeAdvertencia, "Aviso", JOptionPane.WARNING_MESSAGE); 

		return (mensajeAdvertencia.equals("")) ? true : false;
	}
	
	/**
	 * Actualiza la lista de contactos de la agenda
	 */
	public void refrescarLista()
	{
		personasEnLista = agenda.obtenerPersonas();
		ventanaAgenda.cargarContactos(personasEnLista);
		
		//Agregamos la funcion para ver los detalles del contacto a cada boton
		for (Contacto contacto : ventanaAgenda.getContactos())
			contacto.getBtnVerMas().addActionListener(t->mostrarDetalles(t));
	}
	
	public void actualizarPaisesVentanaPersona() 
	{
		this.paises = agenda.obtenerPaises();
		this.ventanaPersona.cargarPaises(paises);
	}
	
	private void actualizarProvincias(ActionEvent ae) 
	{
		int i = ventanaPersona.getCbxPais().getSelectedIndex();
		if (i != -1) //para que no tire error de indice al seleccionar un pais
		{
			PaisDTO pais = paises.get(i);
			provincias = agenda.obtenerProvincias(pais);
			ventanaPersona.cargarProvincias(provincias);
		}
	}


	private void actualizarLocalidades(ActionEvent ae) 
	{
		int i = ventanaPersona.getCbxProvincia().getSelectedIndex();
		if (i != -1) 
		{
			ProvinciaDTO provincia = provincias.get(i);
			localidades = agenda.obtenerLocalidades(provincia);
			ventanaPersona.cargarLocalidades(localidades);
		}
		else
		{
			localidades.clear();
			ventanaPersona.cargarLocalidades(localidades);
		}
	}
	
	private void habilitarIngresoDeDomicilio(ActionEvent ae) 
	{
		if (ventanaPersona.getChckDomicilio().isSelected())
			ventanaPersona.habilitarCamposDomicilio(true);
		else
			ventanaPersona.habilitarCamposDomicilio(false);
	}
	
	/**
	 * Muestra la ventana de detalles de un contacto
	 */
	private void mostrarDetalles(ActionEvent ae) 
	{
		for (int i = 0; i < ventanaAgenda.getContactos().size(); i++) 
		{
			Contacto contacto = ventanaAgenda.getContactos().get(i);
			JButton boton = contacto.getBtnVerMas();
			
			if (boton == ae.getSource()) 
			{
				ventanaDetalles.cargar(personasEnLista.get(i));
				ventanaDetalles.mostrar();
				break;
			}
		}
	}

	/**
	 * Muestra y cierra la ventana de agregar, editar o borrar un tipo de contacto.
	 */
	private void ventanaTipoContacto(ActionEvent ae) 
	{
		ventanaTipoContacto.mostrar();
	}

	
	public void agregarNuevoTipo(ActionEvent ae) 
	{
		String nombre = ventanaTipoContacto.getTxtNombreAgregar().getText().trim();
		
		if (!nombre.equals("")) 
		{
			nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (TipoDTO tipo : tipos) 
			{
				if (tipo.getNombre().equals(nombre))
					existe = true;
			}
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe un tipo de contacto con el nombre '" + nombre + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				agenda.agregarTipoDeContacto(new TipoDTO(0, nombre));
				refrescarTiposDeContacto();
				ventanaTipoContacto.limpiarCampos();
				JOptionPane.showMessageDialog(null, "Nuevo tipo de contacto guardado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
		
	}
	
	public void editarTipo(ActionEvent ae) 
	{
		int seleccionado = ventanaTipoContacto.getCbxTiposEditar().getSelectedIndex();
		String nombreNuevo = ventanaTipoContacto.getTxtNombreEditar().getText().trim();
		
		if (!nombreNuevo.equals("")) 
		{
			nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1); //Convertimos la primera letra en mayuscula
			
			boolean existe = false;
			for (TipoDTO tipo : tipos) {
				if (tipo.getNombre().equals(nombreNuevo))
					existe = true;
			}
			
			if (existe)
				JOptionPane.showMessageDialog(null, "Ya existe un tipo de contacto con el nombre '" + nombreNuevo + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
			else 
			{
				TipoDTO tipo = tipos.get(seleccionado);
				String nombreViejo = tipo.getNombre();
				
				int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan el tipo '" + nombreViejo + "' seran afectados.\n¿Estas seguro de cambiar el tipo '" + nombreViejo + "' por '" + nombreNuevo + "' ?", "Confirmacion para editar", JOptionPane.YES_NO_OPTION);
				
				if (respuesta == 0) 
				{
					tipo.setNombre(nombreNuevo);
					agenda.editarTipoDeContacto(tipo);
					
					refrescarTiposDeContacto();
					ventanaTipoContacto.limpiarCampos();
					refrescarLista();
					JOptionPane.showMessageDialog(null, "Tipo de contacto actualizado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	public void borrarTipo(ActionEvent ae) 
	{
		List<JCheckBox> chksTipos = ventanaTipoContacto.getChksPanelBorrar();
		List<Integer> indicesSelec = new ArrayList<Integer>();
		
		//Recorremos para obtener los indices de los seleccionados
		for (int i = 0; i < chksTipos.size(); i++) 
		{
			if (chksTipos.get(i).isSelected()) 
			{
				indicesSelec.add(i);
				agenda.borrarTipoDeContacto(tipos.get(i));
			}
		}
		
		if (indicesSelec.size() > 0) //Verificamos si selecciono alguno 
		{
			int respuesta = JOptionPane.showConfirmDialog(null, "Los contactos que tengan algunos de los tipos seleccionados seran afectados.\n¿Esta seguro de eliminarlos?", "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
			
			if (respuesta == 0) 
			{
				//Recorremos y eliminamos los tipos que corresponden a los indices seleccionados
				for (Integer indice : indicesSelec) 
				{
					if(chksTipos.get(indice).isSelected())
						agenda.borrarTipoDeContacto(tipos.get(indice));
				}
				
				refrescarTiposDeContacto();
				refrescarLista();
				JOptionPane.showMessageDialog(null, "Tipos de contacto eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		else
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguno.", "Aviso", JOptionPane.WARNING_MESSAGE);
		
	}
	
	private void refrescarTiposDeContacto() 
	{
		tipos = agenda.obtenerTiposDeContacto();
		ventanaPersona.cargarTiposDeContacto(tipos);
		ventanaTipoContacto.cargarTipos(tipos);
		ventanaTipoContacto.limpiarCampos();	
	}
	
	private void ventanaUbicaciones(ActionEvent ae) 
	{
		controladorUbicaciones.iniciar();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) { }
		
}
