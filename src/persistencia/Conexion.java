package persistencia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Conexion 
{
	public static Conexion instancia;
	private Connection connection;
	private Logger log = Logger.getLogger(Conexion.class);	
	
	private Conexion()
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver"); // quitar si no es necesario
			//Establecemos conexión con la base por defecto de MySQL y creamos la base a utilizar
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			crearDB();			
			
			this.connection.setAutoCommit(false);
			log.info("Conexion exitosa");
		}
		catch(Exception e)
		{
			log.error("Conexion fallida", e);
		}
	}
	
	
	private void crearDB() throws SQLException
	{
		//Preparamos la sentencia
		Statement stmt = connection.createStatement();
		
	    String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
	    String tablas = "CREATE TABLE IF NOT EXISTS personas(idPersona SERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,nombre varchar(225) NOT NULL,telefono varchar(225),correo varchar(225),tipo_contacto varchar(50),fecha_cumple varchar(255))";
	    String select_db = "USE grupo_5";
	    
	    
	    /**********************************************/
	    crearTablasLocalizaciones(stmt); //<----- ¡Temporal!
	    /**********************************************/
	    
	    //Ejecutamos las sentencias
	    stmt.executeUpdate(db);
	    stmt.executeUpdate(select_db);
	    

	    //Establecemos conexión con la nueva base y creamos las tablas
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grupo_5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");

	    stmt.executeUpdate(tablas);
	    
		log.info("BD y tablas creadas exitosamente");
		
	}
	
	private void crearTablasLocalizaciones(Statement stmt) throws SQLException
	{
		stmt.execute("USE grupo_5");
		
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
		
	}


	public static Conexion getConexion()   
	{								
		if(instancia == null)
		{
			instancia = new Conexion();
		}
		return instancia;
	}

	public Connection getSQLConexion() 
	{
		return this.connection;
	}
	
	public void cerrarConexion()
	{
		try 
		{
			this.connection.close();
			log.info("Conexion cerrada");
		}
		catch (SQLException e) 
		{
			log.error("Error al cerrar la conexion!", e);
		}
		instancia = null;
	}
}
