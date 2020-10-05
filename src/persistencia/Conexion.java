package persistencia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Conexion 
{
	public static Conexion instancia;
	private Connection connection;
	private Logger log = Logger.getLogger(Conexion.class);	
	
	public Conexion()
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver"); // quitar si no es necesario
			
	        Properties props =  new Properties();
	        props.put("user", "root");
	        props.put("password", "");
	        props.put("allowMultiQueries", "true");
			
			//Establecemos conexión con la base por defecto de MySQL y creamos la base a utilizar
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", props);

			this.connection.setAutoCommit(false);
			log.info("Conexion exitosa");
		}
		catch(Exception e)
		{
			log.error("Conexion fallida", e);
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
	
	public void notificar(String noti) 
	{
		log.info(noti);
	}
	
	public void setConecction(String conect,String user, String pass) throws SQLException 
	{
		this.connection = DriverManager.getConnection(conect,user,pass);
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
