package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import persistencia.Conexion;


public class Servidor {

	
	private String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
    private String tablas = "CREATE TABLE IF NOT EXISTS personas(idPersona SERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,nombre varchar(225) NOT NULL,telefono varchar(225),correo varchar(225),tipo_contacto varchar(50),fecha_cumple varchar(255))";
    private String select_db = "USE grupo_5";
    private Statement stmt;
    private Conexion conexion;
	
	public Servidor() 
	{
		conexion = new Conexion();
	}

	
	public void preparar() throws SQLException 
	{
		
		
		try 
		{
			stmt = conexion.getSQLConexion().createStatement();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		crearTablaPersonas();
		crearTablasUbicaciones();
	
	}
	
	//Crea la base, se conecta a ella y crea la tabla de personas
	private void crearTablaPersonas() throws SQLException 
	{
		
	    stmt.executeUpdate(db);
	    stmt.executeUpdate(select_db);
	    
	    conexion.setConecction("jdbc:mysql://localhost:3306/grupo_5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
	   //conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/grupo_5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  <-- Sirve para cuando se personalize la conexion
		
	    stmt.executeUpdate(tablas);
		conexion.notificar("BD Personas creada/verificada exitosamente");
	}
	
	
	//Crea la tabla de paises, provincias y localidades desde un archivo de texto
	private void crearTablasUbicaciones() 
	{
		String nombreFichero = "localizaciones.txt";
        BufferedReader br = null;
        try {
           br = new BufferedReader(new FileReader(nombreFichero));
           String texto = br.readLine();
           while(texto != null)
           {
        	   stmt.execute(texto);
               texto = br.readLine();
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
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
		
        conexion.notificar("BD Ubicaciones creada/verificada exitosamente");
		
	}
	
	
}
