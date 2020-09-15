package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import modelo.Agenda;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.Vista;
import dto.PersonaDTO;

public class Controlador implements ActionListener
{
		private Vista vista;
		private VentanaPersona ventanaPersona; 
		private Agenda agenda;
		
		private List<PersonaDTO> personasEnLista;
		private PersonaDTO personaSeleccionada;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getBtnEditar().addActionListener(t->ventanaEditarPersona(t));
						
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnActualizarPersona().addActionListener(u-> editarPersona(u));
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
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

		//Muestra la pantalla de edición de contacto y fija el contacto a editar
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
		
		
		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
