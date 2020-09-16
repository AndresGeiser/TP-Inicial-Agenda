package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Servidor;
import vista.VentanaConfigurar;
import vista.VentanaIngreso;

public class ControladorConfigurar implements ActionListener {
	
	private VentanaConfigurar vtnConfigurar;
	private Servidor servidor;
	
	public ControladorConfigurar(Servidor servidor, VentanaConfigurar vtnConfigurar) 
	{
		this.vtnConfigurar = vtnConfigurar;
		this.servidor = servidor;
		
		this.vtnConfigurar.getBtnConfigurar().addActionListener(a-> configurarServidor(a));
	}
	
	public void iniciar() 
	{
		boolean esPrimeraEjcucion = true; //¡¡Hay que ver la forma en como sabremos cuando se ejecuta por primera vez!!!
		
		if(esPrimeraEjcucion) 
		{
			vtnConfigurar.mostrar();
		}
		else
		{	
			//Mostramos la ventana de Ingreso
			VentanaIngreso vista = new VentanaIngreso();
			ControladorIngreso controlador = new ControladorIngreso(servidor, vista);
			controlador.iniciar();
		}
	}
	
	public void configurarServidor(ActionEvent p) 
	{
		/****
		 * 
		 * REALIZAR CONFIGURACION - IMPLEMENTAR
		 * 
		 * *****/
		
		
		//Cerramos la ventana de configuracion
		vtnConfigurar.dispose();
		
		//Mostramos la ventana de Ingreso
		VentanaIngreso vista = new VentanaIngreso();
		ControladorIngreso controlador = new ControladorIngreso(servidor, vista);
		controlador.iniciar();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
}
