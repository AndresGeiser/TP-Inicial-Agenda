package persistencia;


import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import controlador.ControladorAgenda;
import controlador.ControladorConfigurar;
import modelo.Servidor;
import vista.VentanaConfigurar;


public class Conexion 
{
	public static Conexion instancia;
	private Connection connection;
	private Logger log = Logger.getLogger(Conexion.class);	
	private DBConfiguracion data = DBConfiguracion.getDBConfiguracion();
	private VentanaConfigurar ventanaConfigurar;
	
	public Conexion()
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver"); // quitar si no es necesario
			
	        Properties props =  new Properties();
	        props.put("user", data.getPropiedad("user"));
	        props.put("password", data.getPropiedad("password"));
	        props.put("allowMultiQueries", "true");
	        
	       	data.setPropiedad("allowMultiQueries", "true");		
			//Establecemos conexión con la base por defecto de MySQL y creamos la base a utilizar
			//this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", props);

			this.connection = DriverManager.getConnection("jdbc:mysql://"+data.getPropiedad("host")+ ":"+ data.getPropiedad("port") + "/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", props);

			this.connection.setAutoCommit(false);
			log.info("Conexion exitosa");
		}
		catch(Exception e)
		{		
			JOptionPane.showMessageDialog(null, "La conexion no se ha podido establecer, vuelva a configurar", "Aviso", JOptionPane.WARNING_MESSAGE);

			this.ventanaConfigurar = VentanaConfigurar.getInstance();
			
			this.ventanaConfigurar.setTxtUsuario(data.getPropiedad("user"));
			this.ventanaConfigurar.setTxtContraseña(data.getPropiedad("password"));
			this.ventanaConfigurar.setTxtPuerto(data.getPropiedad("port"));
			this.ventanaConfigurar.setTxtHost(data.getPropiedad("host"));

			this.ventanaConfigurar.mostrar();
			this.ventanaConfigurar.getBtnConfigurar().addActionListener(a -> actualizarDatos(a));

			
			
			
			
			
			
			log.error("Conexion fallida", e);
		}
	}
	
	private void actualizarDatos(ActionEvent ae) 
	{
		
		data.setPropiedad("user", this.ventanaConfigurar.getTxtUsuario());
		data.setPropiedad("password", this.ventanaConfigurar.getTxtContraseña());
		data.setPropiedad("host", this.ventanaConfigurar.getTxtHost());
		data.setPropiedad("port", this.ventanaConfigurar.getTxtPuerto());
		
		data.crearConfig();
		
		JOptionPane.showMessageDialog(null, "Vuelva a iniciar la aplicacion", "Aviso", JOptionPane.WARNING_MESSAGE);
		this.ventanaConfigurar.cerrar();
		System.exit(0);
	}
	

	public DBConfiguracion getData() 
	{
		return this.data;
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
