package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import modelo.Agenda;
import modelo.Servidor;
import persistencia.Conexion;
import vista.VentanaAgenda;
import vista.VentanaConfigurar;

public class ControladorConfigurar implements ActionListener {
	
	private Servidor servidor;


	
	public ControladorConfigurar(Servidor servidor)
	{
		this.servidor = servidor;
		configurarServidor();
	}
	
	public void iniciar()
	{
		boolean esPrimeraEjcucion = true; //¡¡Hay que ver la forma en como sabremos cuando se ejecuta por primera vez!!! <-- Investigar si se puede consultar si la base ya fue creada
		
		if(esPrimeraEjcucion) 
		{
			//vtnConfigurar.mostrar();
		}
		else
		{	
			//Abrimos la ventana de ingreso
			//mostrarVentanaDeIngreso();
		}
	}
	
	private void configurarServidor()
	{
		servidor.configurar();

		ControladorIngreso controlador = new ControladorIngreso(servidor);
	};

	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
}
