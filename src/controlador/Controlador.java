package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelo.Agenda;
import persistencia.PersonaDAO;
import reportes.ReporteAgenda;
import vista.Contacto;
import vista.VentanaPersona;
import vista.Vista;
import dto.PersonaDTO;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personasEnTabla;
		private VentanaPersona ventanaPersona; 
		private Agenda agenda;
		private PersonaDTO personaSeleccionada;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getBtnEditar().addActionListener(t->mostrarPersonaEditar(t));
						
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnActualizarPersona().addActionListener(u-> editarPersona(u));
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.agenda = agenda;
		}
		
		private void ventanaAgregarPersona(ActionEvent a) {
			this.ventanaPersona.mostrarVentana();
		}

		private void guardarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String correo = ventanaPersona.getTxtCorreo().getText();
			String tipo = ventanaPersona.getTipo().getSelectedItem().toString();
			String cumple = ventanaPersona.getTxtCumple().getText();

			
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel,correo,tipo,cumple);
			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}

		private void mostrarReporte(ActionEvent r) {
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();	
		}

		public void borrarPersona(ActionEvent s)
		{
			Contacto[] contactos = this.vista.getContactos();
			
			for (int i = 0; i < contactos.length; i++) {
				if(contactos[i].estaSeleccionado()) {
					this.agenda.borrarPersona(this.personasEnTabla.get(i));
				}
			}
			
			this.refrescarTabla();
		}
		
		public void editarPersona(ActionEvent e)
		{
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String correo = ventanaPersona.getTxtCorreo().getText();
			String tipo = ventanaPersona.getTipo().getSelectedItem().toString();
			String cumple = ventanaPersona.getTxtCumple().getText();
			
			personaSeleccionada.setNombre(nombre);
			personaSeleccionada.setCorreo(correo);
			personaSeleccionada.setTelefono(tel);
			personaSeleccionada.setTipo_contacto(tipo);
			personaSeleccionada.setFecha_cumple(cumple);

			
			
			this.agenda.editarPersona(personaSeleccionada);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();

		}
		
		//Muestra la pantalla de edición de contacto y fija el contacto a editar
		public void mostrarPersonaEditar(ActionEvent s)
		{
			Contacto[] contactos = this.vista.getContactos();
			
			for (int i = 0; i < contactos.length; i++) {
				if(contactos[i].estaSeleccionado()) {
					PersonaDTO seleccionado = contactos[i].getContacto();
					
					String nombre = seleccionado.getNombre();
					String telefono = seleccionado.getTelefono();
					String correo = seleccionado.getCorreo();
					String cumple = seleccionado.getFecha_cumple();

					personaSeleccionada = seleccionado;
					
					this.ventanaPersona.mostrarVentanaConDatos(nombre,telefono,correo,cumple);
				}
			}
			
			this.refrescarTabla();
			
		}
		
		public void inicializar()
		{
			this.refrescarTabla();
			this.vista.show();
		}
		
		private void refrescarTabla()
		{
			this.personasEnTabla = agenda.obtenerPersonas();
			this.vista.llenarTabla(this.personasEnTabla);
		}
		

		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
