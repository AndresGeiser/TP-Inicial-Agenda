package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Agenda;
import modelo.Servidor;
import persistencia.DAOSQLFactory;
import vista.VentanaAgenda;

public class ControladorIngreso implements ActionListener {
	
	private Servidor servidor;
	
	public ControladorIngreso(Servidor servidor) 
	{
		this.servidor = servidor;
		
		validarIngreso();
		
	}

	
	public void validarIngreso() 
	{			
			//Mostramos la ventana de la agenda
			VentanaAgenda vista = VentanaAgenda.getInstance();
			Agenda modelo = new Agenda(new DAOSQLFactory());
			ControladorAgenda controlador = ControladorAgenda.getInstance(vista, modelo);
			controlador.inicializar();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
}
