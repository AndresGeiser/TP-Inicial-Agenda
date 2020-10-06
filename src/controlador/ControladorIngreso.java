package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Agenda;
import modelo.Servidor;
import persistencia.DAOSQLFactory;
import vista.VentanaAgenda;
import vista.VentanaIngreso;

public class ControladorIngreso implements ActionListener {
	
	private VentanaIngreso vtnIngreso;
	private Servidor servidor;
	
	public ControladorIngreso(Servidor servidor, VentanaIngreso vtnIngreso) 
	{
		this.vtnIngreso = vtnIngreso;
		this.servidor = servidor;
		
		this.vtnIngreso.getBtnIngresar().addActionListener(a-> validarIngreso(a));
	}
	
	public void iniciar() 
	{
		vtnIngreso.mostrar();	
	}
	
	public void validarIngreso(ActionEvent p) 
	{
		boolean ingresoValido = true; //¡¡¡IMPLEMENTAR LUEGO LA VALIDACION!!!
		
		if(ingresoValido) 
		{
			//Cerramos la ventana de ingreso
			vtnIngreso.dispose(); 
			
			//Mostramos la ventana de la agenda
			VentanaAgenda vista = VentanaAgenda.getInstance();
			Agenda modelo = new Agenda(new DAOSQLFactory());
			ControladorAgenda controlador = ControladorAgenda.getInstance(vista, modelo);
			controlador.inicializar();
		}
		
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
}
