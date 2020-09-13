package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			crearDB();
			//this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","abc123");

			//crearDB();
			
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
		Statement stmt = connection.createStatement();

	    String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
	    String tablas = "CREATE TABLE IF NOT EXISTS personas(idPersona SERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,nombre varchar(225) NOT NULL,telefono varchar(225))";
	    String select_db = "USE grupo_5";
	    

	    stmt.executeUpdate(db);

	    stmt.executeUpdate(select_db);
	    
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grupo_5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");

	    stmt.executeUpdate(tablas);
	    
	    

	    
		/*
		PreparedStatement statement;
		String db = "CREATE DATABASE IF NOT EXISTS grupo_5";
		String tablas = "CREATE TABLE IF NOT EXISTS personas(idPersona SERIAL NOT NULL AUTO_INCREMENT PRIMARY KEY,nombre varchar(225) NOT NULL,telefono varchar(225))";
		
		statement = this.connection.prepareStatement(db);


		statement.executeUpdate(db);
		
		
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grupo_5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");

		
		statement.executeUpdate(tablas);

		
		statement.close();
		 */
		log.info("BD y tablas creadas exitosamente");
		
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
