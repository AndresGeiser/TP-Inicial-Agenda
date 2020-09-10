package main;

import modelo.Agenda;
import persistencia.DAOSQLFactory;
import controlador.Controlador;
import vista.Vista;


public class Main 
{

	public static void main(String[] args) 
	{
		Vista vista = new Vista();
		Agenda modelo = new Agenda(new DAOSQLFactory());
		Controlador controlador = new Controlador(vista, modelo);
		controlador.inicializar();
	}
}
