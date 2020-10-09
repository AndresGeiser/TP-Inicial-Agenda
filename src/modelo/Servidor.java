package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

	
	public void configurar()	//HAY QUE USAR LOS STRINGS PARA CREAR EL USUARIO 
	{	try 
		{
			crearDB();
			crearTablas();
			
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
		String rutaArchivo = "/sql/agenda.sql";
        BufferedReader br = null;
        try {
        	InputStream is = getClass().getResourceAsStream(rutaArchivo);
        	InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        	
        	br = new BufferedReader(isr);
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
	
}
