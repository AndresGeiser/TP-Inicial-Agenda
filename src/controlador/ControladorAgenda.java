package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import modelo.Agenda;
import modelo.Regiones;
import persistencia.DAOSQLFactory;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.VentanaAgenda;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;

public class ControladorAgenda implements ActionListener
{
		private VentanaAgenda vista;
		private VentanaPersona ventanaPersona; 
		private Agenda agenda;
		private Regiones regiones;
		
		private List<PersonaDTO> personasEnLista;
		private PersonaDTO personaSeleccionada;
		
		private List<PaisDTO> paises;
		private List<ProvinciaDTO> provincias;
		private List<LocalidadDTO> localidades;
		
		public ControladorAgenda(VentanaAgenda vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getBtnEditar().addActionListener(t->ventanaEditarPersona(t));
						
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnActualizarPersona().addActionListener(u-> editarPersona(u));
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaPersona.getCbxPais().addActionListener(p->actualizarProvincias(p));
			this.ventanaPersona.getCbxProvincia().addActionListener(p->actualizarLocalidades(p));
	
			//Carga de paises
			this.regiones = new Regiones(new DAOSQLFactory());
			this.paises = this.regiones.obtenerPaises();
			this.ventanaPersona.cargarPaises(this.paises);
			
			this.agenda = agenda;
		}
		

		private void ventanaAgregarPersona(ActionEvent a) {
			ventanaPersona.mostrarVentana();
		}

		private void guardarPersona(ActionEvent p) {

			if(validarDatos()) {
				String nombre = ventanaPersona.getTxtNombre().getText();
				String tel = ventanaPersona.getTxtTelefono().getText();
				String correo = ventanaPersona.getTxtCorreo().getText();
				String tipo = ventanaPersona.getTipo().getSelectedItem().toString();
				String cumple = ventanaPersona.getTxtCumple().getText();

				
				PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel,correo,tipo,cumple);
				agenda.agregarPersona(nuevaPersona);
				refrescarTabla();
				ventanaPersona.cerrar();	
			}
			
		}

		//Muestra la pantalla de edici�n de contacto y fija el contacto a editar
		public void ventanaEditarPersona(ActionEvent s)
		{
			Contacto[] contactos = vista.getContactos();
			
			for (int i = 0; i < contactos.length; i++) {
				if(contactos[i].estaSeleccionado()) {
					personaSeleccionada = personasEnLista.get(i);
					break;
				}
			}
			
			if(personaSeleccionada == null) {
				JOptionPane.showMessageDialog(null, "Seleccione el contacto que desea editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			} else {
				String nombre = personaSeleccionada.getNombre();
				String telefono = personaSeleccionada.getTelefono();
				String correo = personaSeleccionada.getCorreo();
				String tipo = personaSeleccionada.getTipo_contacto();
				String cumple = personaSeleccionada.getFecha_cumple();

				ventanaPersona.mostrarVentanaConDatos(nombre, telefono, correo, tipo, cumple);
			}
			
			this.refrescarTabla();
			
		}
		
		public void editarPersona(ActionEvent e)
		{
			if(validarDatos()) {
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
				
				agenda.editarPersona(personaSeleccionada);
				refrescarTabla();
				ventanaPersona.cerrar();
				personaSeleccionada = null;

			}
		}
		
		
		private boolean validarDatos() {
			String nombre = ventanaPersona.getTxtNombre().getText().trim();
			String tel = ventanaPersona.getTxtTelefono().getText().trim();
			String correo = ventanaPersona.getTxtCorreo().getText().trim();
			
			String mensajeAdvertencia = "";
			
			if(nombre.equals("") || tel.equals("")) {
				mensajeAdvertencia += "El nombre y telefono del contacto es requerido.\n";
			}
			
			if(nombre.length() > 20){
				mensajeAdvertencia += "Nombre de contacto muy largo. (Maximo 20 caracteres)\\n";
			}
			else {
				for (PersonaDTO personaDTO : personasEnLista) {
					if(personaDTO.getNombre().equals(nombre) && personaDTO.getIdPersona() != personaSeleccionada.getIdPersona()) {
						mensajeAdvertencia += "Ya existe un contacto con el nombre: '" + nombre + "'. \n";
						return false;
					}
				}
			}
			
			if(!correo.equals("")) {
				String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
				Pattern pattern = Pattern.compile(emailPattern);
				Matcher matcher = pattern.matcher(correo);
				if (!matcher.matches()) {
					mensajeAdvertencia += "Verifique que el correo este bien escrito.\n";
				}
			} 
			
			if(!mensajeAdvertencia.equals("")) {
				JOptionPane.showMessageDialog(null, mensajeAdvertencia, "Aviso", JOptionPane.WARNING_MESSAGE); 
			}
			
			return (mensajeAdvertencia.equals("")) ? true : false;
		}
		

		public void borrarPersona(ActionEvent s)
		{
			Contacto[] contactos = vista.getContactos();
			
			for (int i = 0; i < contactos.length; i++) {
				if(contactos[i].estaSeleccionado()) {
					agenda.borrarPersona(personasEnLista.get(i));
				}
			}
			
			refrescarTabla();
		}
		
		
		private void mostrarReporte(ActionEvent r) {
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();	
		}
		
		
		public void inicializar()
		{
			refrescarTabla();
			vista.show();
		}
		
		
		private void refrescarTabla()
		{
			personasEnLista = agenda.obtenerPersonas();
			vista.llenarTabla(personasEnLista);
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
			ProvinciaDTO provincia = provincias.get(i);
			localidades = regiones.obtenerLocalidades(provincia);
			ventanaPersona.cargarLocalidades(localidades);
		}

		
		@Override
		public void actionPerformed(ActionEvent e) { }
		
}