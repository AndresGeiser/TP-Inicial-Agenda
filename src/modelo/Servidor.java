package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Statement;
import persistencia.Conexion;


public class Servidor {

	private String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
    private String tablaPersona = "CREATE TABLE IF NOT EXISTS personas(id SERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY, nombre varchar(20) NOT NULL  UNIQUE, telefono varchar(20) NOT NULL, correo varchar(100), tipo_contacto enum('Trabajo', 'Familia', 'Universidad', 'Amigos') NOT NULL, fecha_cumple varchar(255), pais varchar(255), provincia varchar(255), localidad varchar(255), calle varchar(255), altura varchar(6), tipo_domicilio enum('', 'Casa', 'Edificio'), piso varchar(3), dpto varchar(3))";
    private String usarDB = "USE grupo_5";
    
    private Conexion conexion;
    private Statement stmt;
	
	public Servidor() 
	{
		conexion = Conexion.getConexion();
		try {
			stmt = conexion.getSQLConexion().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void configurar(String usuario, String contraseña)	//HAY QUE USAR LOS STRINGS PARA CREAR EL USUARIO 
	{	try 
		{
			crearDB();
			crearTablaPersonas();
			crearTablasUbicaciones();
			
			conexion.getSQLConexion().commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	private void crearDB() throws SQLException 
	{
		if(stmt.executeUpdate(db) > 0) 
			conexion.notificar("Base de datos 'grupo_5' creada.");
		
		if(stmt.executeUpdate(usarDB) > 0)
			conexion.notificar("Conectado con base de datos 'grupo_5'.");
	}
	
	
	private void crearTablaPersonas() throws SQLException 
	{
    	if(stmt.executeUpdate(tablaPersona) > 0)
    		conexion.notificar("Tabla 'Personas' creada/verificada exitosamente");
	}
	
	
	//Crea la tabla de paises, provincias y localidades desde un archivo de texto
	private void crearTablasUbicaciones() throws SQLException 
	{
		String nombreFichero = "localizaciones.txt";
        BufferedReader br = null;
     
        try 
        {
        	Statement stmt = conexion.getSQLConexion().createStatement();
        	br = new BufferedReader(new FileReader(nombreFichero));
        	String texto = br.readLine();
        	while(texto != null)
        	{
        		stmt.executeUpdate(texto);
        		texto = br.readLine();
	  		}
   
        	conexion.notificar("Tablas 'Pais', 'Provincia' y 'Localidad' creadas exitosamente");
        }
        catch (FileNotFoundException e) 
        {
        	System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }

        catch(Exception e) 
        {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally 
        {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
		
	}
	
}
