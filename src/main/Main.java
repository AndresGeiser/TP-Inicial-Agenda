package main;

import modelo.Servidor;
import java.sql.SQLException;
import controlador.ControladorConfigurar;
import vista.VentanaConfigurar;

public class Main 
{
	public static void main(String[] args) throws SQLException 
	{
		VentanaConfigurar vista = new VentanaConfigurar();
		Servidor modelo = new Servidor();
		ControladorConfigurar controlador = new ControladorConfigurar(modelo, vista);
		controlador.iniciar();
	}
}
