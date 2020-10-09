package main;

import modelo.Servidor;
import java.sql.SQLException;
import java.util.StringTokenizer;

import controlador.ControladorConfigurar;

public class Main 
{
	public static void main(String[] args) throws SQLException 
	{
		//VentanaConfigurar vista = new VentanaConfigurar();
		Servidor modelo = new Servidor();
		ControladorConfigurar controlador = new ControladorConfigurar(modelo);
		controlador.iniciar();
	}
}
