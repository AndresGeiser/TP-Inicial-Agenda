package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Statement;
import persistencia.Conexion;


public class Servidor 
{
	private String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
	private String usarDB = "USE grupo_5";
    private Conexion conexion;
    private Statement stmt;
	
	public Servidor() 
	{
		conexion = Conexion.getConexion();
		try 
		{
			stmt = conexion.getSQLConexion().createStatement();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	
	public void configurar(String usuario, String contraseña)	//HAY QUE USAR LOS STRINGS PARA CREAR EL USUARIO 
	{	try 
		{
			crearDB();
			crearTablas();
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
	
	
	private void crearTablas() throws SQLException 
	{
		String contenido = "";
		String nombreFichero = "contactos.sql";
        BufferedReader br = null;
     
        try {
        	br = new BufferedReader(new FileReader(nombreFichero));
        	String linea = br.readLine();
        	while(linea != null)
        	{
        		contenido += linea;
        		linea = br.readLine();
	  		}
  
        }
        catch (FileNotFoundException e) {
        	System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }

        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
                
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
        
        stmt.executeUpdate(contenido);
        
	}
	
	
	//Crea la tabla de paises, provincias y localidades desde un archivo de texto
	private void crearTablasUbicaciones() throws SQLException 
	{
		String nombreFichero = "localizaciones.sql";
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
   
        	conexion.notificar("Tablas 'pais', 'provincia' y 'localidad' creadas exitosamente");
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
