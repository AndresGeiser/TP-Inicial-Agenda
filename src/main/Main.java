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
		
		//VentanaAgenda vista = new VentanaAgenda();
		//Agenda modelo = new Agenda(new DAOSQLFactory());
		//ControladorAgenda controlador = new ControladorAgenda(vista, modelo);
		//controlador.inicializar();
		
	}
}
