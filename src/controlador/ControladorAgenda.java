package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modelo.Agenda;
import persistencia.Conexion;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.VentanaTipoContacto;
import vista.VentanaUbicaciones;
import vista.VentanaAgenda;
import vista.VentanaConfigurar;
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
	private VentanaConfigurar ventanaConfigurar;
	
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
		this.ventanaConfigurar = VentanaConfigurar.getInstance();
		this.ventanaConfigurar.getBtnConfigurar().addActionListener(a -> actualizarDatos(a));
		
		this.ventanaAgenda = vista;
		this.ventanaAgenda.getBtnAgregar().addActionListener(a -> ventanaAgregarPersona(a));
		this.ventanaAgenda.getBtnBorrar().addActionListener(a -> borrarContactos(a));
		this.ventanaAgenda.getBtnReporte().addActionListener(a -> mostrarReporte(a));
		this.ventanaAgenda.getBtnEditar().addActionListener(a -> ventanaEditarPersona(a));
		this.ventanaAgenda.getBtnConfigurar().addActionListener(a -> ventanaConfigurar(a));
					
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
	
	
	private void ventanaConfigurar(ActionEvent ae)
	{
		Conexion con = Conexion.getConexion();
		
		this.ventanaConfigurar.setTxtUsuario(con.getData().getPropiedad("user"));
		this.ventanaConfigurar.setTxtContraseña(con.getData().getPropiedad("password"));
		this.ventanaConfigurar.setTxtPuerto(con.getData().getPropiedad("port"));
		this.ventanaConfigurar.setTxtHost(con.getData().getPropiedad("host"));

		this.ventanaConfigurar.mostrar();
	}

	private void actualizarDatos(ActionEvent ae) 
	{
		Conexion con = Conexion.getConexion();
		
		con.getData().setPropiedad("user", this.ventanaConfigurar.getTxtUsuario());
		con.getData().setPropiedad("password", this.ventanaConfigurar.getTxtContraseña());
		con.getData().setPropiedad("host", this.ventanaConfigurar.getTxtHost());
		con.getData().setPropiedad("port", this.ventanaConfigurar.getTxtPuerto());
		
		con.getData().crearConfig();
		
		JOptionPane.showMessageDialog(null, "Las configuraciones se aplicaran al reiniciar", "Aviso", JOptionPane.WARNING_MESSAGE);
		this.ventanaConfigurar.cerrar();
	}

	
	
	public void inicializar()
	{
		actualizarPaises();
		
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
			
			String cumple = null;
			if(ventanaPersona.getJdtFechaCumpleanios().getDate() != null) 
				cumple = new SimpleDateFormat("yyyy-MM-dd").format(ventanaPersona.getJdtFechaCumpleanios().getDate().getTime());
			
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
			ventanaPersona.cerrar();	
			refrescarLista();
		}
	}

	/**
	 * Muestra la ventana para editar un contacto
	 */
	public void ventanaEditarPersona(ActionEvent ae)
	{
		List<Contacto> contactos = ventanaAgenda.getContactos();
		
		int cantSeleccionados = 0;
		for (int i = 0; i < contactos.size(); i++) 
		{
			if (contactos.get(i).estaSeleccionado()) 
			{
				personaSeleccionada = personasEnLista.get(i);
				cantSeleccionados++;
			}
		}
		
		if(cantSeleccionados == 0)
			JOptionPane.showMessageDialog(null, "Seleccione el contacto que desea editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
		else if (cantSeleccionados > 1)
		{
			JOptionPane.showMessageDialog(null, "No puede tener mas de un contacto seleccionado para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			personaSeleccionada = null;
		}
		else
			ventanaPersona.mostrarVentanaConDatos(personaSeleccionada);
	}
	
	public void editarPersona(ActionEvent ae)
	{
		if (validarDatos()) 
		{
			String nombre = ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String correo = ventanaPersona.getTxtCorreo().getText();
			
			String cumple = null;
			if(ventanaPersona.getJdtFechaCumpleanios().getDate() != null) 
				cumple = new SimpleDateFormat("yyyy-MM-dd").format(ventanaPersona.getJdtFechaCumpleanios().getDate().getTime());
			
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
			JOptionPane.showMessageDialog(null, "Contacto actualizado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	/**
	 * Borra los contactos seleccionados
	 */
	public void borrarContactos(ActionEvent ae)
	{
		List<Contacto> contactos = ventanaAgenda.getContactos();
	
		boolean selecciono = false;
		for (Contacto contacto : contactos) 
		{
			if (contacto.estaSeleccionado()) 
			{
				selecciono = true;
				break;
			}
		}
		
		if(!selecciono) 
		{
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun contacto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar los contactos seleccionados?", "Confirmacion para eliminar", JOptionPane.YES_NO_OPTION);
		if (respuesta == 0) 
		{
			for (int i = 0; i < contactos.size(); i++) 
				if (contactos.get(i).estaSeleccionado()) 
					agenda.borrarPersona(personasEnLista.get(i));
			
			refrescarLista();
			JOptionPane.showMessageDialog(null, "Contactos eliminados correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
	/**
	 * Retorna true si los campos de la ventana de agregar o editar un contacto estan correctos o false en caso contrario
	 */
	private boolean validarDatos() 
	{	
		String mensajeAdvertencia = "";
		int numMensaje = 1;
		
		//Validacion nombre
		String nombre = ventanaPersona.getTxtNombre().getText().trim();
		if (nombre.equals("")) 
			mensajeAdvertencia += numMensaje++ + ") El nombre del contacto es requerido.\n";
		if (nombre.length() > 20)
			mensajeAdvertencia += numMensaje++ + ") Nombre de contacto muy largo. (Maximo 20 caracteres)\n";

		
		//Validacion telefono
		String tel = ventanaPersona.getTxtTelefono().getText().trim();
		if(tel.equals("")) 
			mensajeAdvertencia += numMensaje++ + ") El número de telefono del contacto es requerido.\n";
		else if (tel.length() > 15 || tel.length() < 7)
			mensajeAdvertencia += numMensaje++ + ") El número invalido. (Minimo 7 numeros y maximo 15)\n";
		
		//Validacion correo
		String correo = ventanaPersona.getTxtCorreo().getText().trim();
		if (!correo.equals("")) 
		{
			String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
			Pattern pattern = Pattern.compile(emailPattern);
			Matcher matcher = pattern.matcher(correo);
			if (!matcher.matches()) 
				mensajeAdvertencia += numMensaje++ + ") Verifique que el correo este bien escrito.\n";
		}
		
		//Validacion fecha
		String textoFecha = ((JTextField) ventanaPersona.getJdtFechaCumpleanios().getDateEditor().getUiComponent()).getText().trim();
		if(!textoFecha.isEmpty()) 
		{
			Date fecha = ventanaPersona.getJdtFechaCumpleanios().getDate();
			if(fecha == null) 
				mensajeAdvertencia += numMensaje++ + ") La fecha de cumpleaños es invalida.\n";
		}
		
		
		//Validacion Domicilio
		boolean agregoDomicilio = ventanaPersona.getChckDomicilio().isSelected();
		if (agregoDomicilio) 
		{
			int cantPaises = ventanaPersona.getCbxPais().getItemCount();
			if (cantPaises > 0) 
			{
				int cantProvincias = ventanaPersona.getCbxProvincia().getItemCount();
				if(cantProvincias > 0) 
				{
					int cantLocalidades = ventanaPersona.getCbxLocalidad().getItemCount();
					if(cantLocalidades > 0) 
					{
						String calle = ventanaPersona.getTxtCalle().getText().trim();
						String altura = ventanaPersona.getTxtAltura().getText().trim();
						
						if (calle.equals("") || altura.equals("")) 
							mensajeAdvertencia += numMensaje++ + ") No puede agregar un domicilio sin introducir la calle y altura.\n";
					}
					else
						mensajeAdvertencia += numMensaje++ + ") No puede agregar un domicilio sin escoger una localidad.\n";
				}
				else
					mensajeAdvertencia += numMensaje++ + ") No puede agregar un domicilio sin escoger una provincia.\n";
			}
			else
				mensajeAdvertencia += numMensaje++ + ") No puede agregar un domicilio sin escoger un pais.\n";
		}
		
		boolean noHayErrores = (mensajeAdvertencia.equals("")) ? true : false; 
		if (noHayErrores == false) 
			JOptionPane.showMessageDialog(null, "Se encontraron los siguientes errores en los campos:\n\n" + mensajeAdvertencia, "Aviso", JOptionPane.WARNING_MESSAGE); 

		return noHayErrores;
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
	 * Actualiza la lista de contactos de la agenda
	 */
	public void refrescarLista()
	{
		personasEnLista = agenda.obtenerPersonas();
		ventanaAgenda.cargarContactos(personasEnLista);
		
		//Agregamos la funcion para ver los detalles del contacto a cada boton
		for (Contacto contacto : ventanaAgenda.getContactos())
			contacto.getBtnVerMas().addActionListener(t -> ventanaDetalles(t));
	}
	
	
	public void actualizarPaises() 
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
	private void ventanaDetalles(ActionEvent ae) 
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
	 * Muestra la ventana de agregar, editar o borrar un tipo de contacto.
	 */
	private void ventanaTipoContacto(ActionEvent ae) 
	{
		ventanaTipoContacto.mostrar();
	}

	
	public void agregarNuevoTipo(ActionEvent ae) 
	{
		String nombreTipo = ventanaTipoContacto.getTxtNombreAgregar().getText().trim();
		
		if(validarNombreTipoContacto(nombreTipo)) 
		{
			nombreTipo = nombreTipo.substring(0, 1).toUpperCase() + nombreTipo.substring(1).toLowerCase(); //Convertimos la primera letra en mayuscula
			agenda.agregarTipoDeContacto(new TipoDTO(0, nombreTipo));
			refrescarTiposDeContacto();
			ventanaTipoContacto.limpiarCampos();
			JOptionPane.showMessageDialog(null, "Nuevo tipo de contacto guardado.", "Aviso", JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
	public void editarTipo(ActionEvent ae) 
	{
		int seleccionado = ventanaTipoContacto.getCbxTiposEditar().getSelectedIndex();
		String nombreNuevo = ventanaTipoContacto.getTxtNombreEditar().getText().trim();
		
		if(validarNombreTipoContacto(nombreNuevo)) 
		{
			nombreNuevo = nombreNuevo.substring(0, 1).toUpperCase() + nombreNuevo.substring(1).toLowerCase();
			
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
	
	private boolean validarNombreTipoContacto(String nombre) 
	{
		if (nombre.equals("")) 
		{
			JOptionPane.showMessageDialog(null, "El campo esta vacio.", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return false;
		}
		
		if(nombre.length() > 15) 
		{
			JOptionPane.showMessageDialog(null, "Introduce un nombre que no supere los 15 caracteres", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return false;
		}
		
		if(nombre.contains(" ")) 
		{
			JOptionPane.showMessageDialog(null, "Introduce un nombre que no tenga espacios", "Aviso", JOptionPane.WARNING_MESSAGE); 
			return false;
		}
		
		nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		for (TipoDTO tipo : tipos) 
		{
			if (tipo.getNombre().equalsIgnoreCase(nombre)) 
			{
				JOptionPane.showMessageDialog(null, "Ya existe un tipo de contacto con el nombre '" + nombre + "'", "Aviso", JOptionPane.WARNING_MESSAGE); 
				return false;
			}
		}
		
		return true;
	}
	
	private void refrescarTiposDeContacto() 
	{
		tipos = agenda.obtenerTiposDeContacto();
		ventanaPersona.cargarTiposDeContacto(tipos);
		ventanaTipoContacto.cargarTipos(tipos);
		ventanaTipoContacto.limpiarCampos();	
	}
	
	
	/**
	 * Muestra la ventana de agregar, editar o borrar ubicaciones.
	 */
	private void ventanaUbicaciones(ActionEvent ae) 
	{
		controladorUbicaciones.iniciar();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) { }
		
}
