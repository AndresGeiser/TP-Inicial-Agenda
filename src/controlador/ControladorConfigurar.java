package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import modelo.Servidor;
import vista.VentanaConfigurar;
import vista.VentanaIngreso;

public class ControladorConfigurar implements ActionListener {
	
	private VentanaConfigurar vtnConfigurar;
	private Servidor servidor;
	
	public ControladorConfigurar(Servidor servidor, VentanaConfigurar vtnConfigurar) throws SQLException 
	{
		this.vtnConfigurar = vtnConfigurar;
		this.servidor = servidor;
		
		this.vtnConfigurar.getBtnConfigurar().addActionListener(a-> configurarServidor(a));
	}
	
	public void iniciar()
	{
		boolean esPrimeraEjcucion = true; //¡¡Hay que ver la forma en como sabremos cuando se ejecuta por primera vez!!! <-- Investigar si se puede consultar si la base ya fue creada
		
		if(esPrimeraEjcucion) 
		{
			vtnConfigurar.mostrar();
		}
		else
		{	
			//Abrimos la ventana de ingreso
			mostrarVentanaDeIngreso();
		}
	}
	
	private void configurarServidor(ActionEvent p)
	{
		String usuario = vtnConfigurar.getTxtUsuario().getText().trim();
		String contraseña = vtnConfigurar.getTxtContraseña().getText().trim();
		
		servidor.configurar(usuario, contraseña);
		
		//Cerramos la ventana de configuracion
		vtnConfigurar.dispose();
		
		//Abrimos la ventana de ingreso
		mostrarVentanaDeIngreso();
	}
	
	private void mostrarVentanaDeIngreso() 
	{
		VentanaIngreso vista = new VentanaIngreso();
		ControladorIngreso controlador = new ControladorIngreso(servidor, vista);
		controlador.iniciar();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
	}
	
}
