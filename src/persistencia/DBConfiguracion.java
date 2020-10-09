package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfiguracion 
{
	public static DBConfiguracion instancia;
	private Properties propiedadesDB;
	
	
	public DBConfiguracion() //Se le pueden pasar los parametros para configurar la conexion
	{
	    System.setProperty("Jdbc.drivers", "com.mysql.jdbc.Driver");
	   
		//Levantamos el archivo de propiedades, si no existe creamos el directorio
		propiedadesDB = new Properties();
		try 
		{
			propiedadesDB.load(new FileInputStream("./configDB.properties")); 
		} 
		catch (IOException e) 
		{
			
			//Se crea el archivo con la configuracion inicial
			crearConfig();
		}
	
		
	}
	
	public static DBConfiguracion getDBConfiguracion()   
	{								
		if(instancia == null)
		{
			instancia = new DBConfiguracion();
		}
		return instancia;
	}
	
	//Este metodo podria recibir como argumentos los que setee el usuario - ojo este metodo solo se usa para la primera vez! (o deberia)
	public void crearConfig()
	{
		 
		 FileOutputStream arch = null;	   	    
			try 
			{
			  arch=new FileOutputStream("./configDB.properties");
			  propiedadesDB.store(arch, "Fichero de Propiedades de la conexion");
			}
			catch(IOException ioe) 
				{ioe.printStackTrace();}
		
	}
	
	//Devuelve el String "1" si ya se configuro la conexion al menos una vez, puede usarse para mostrar la configuracion solo la primera vez
	public String getPropiedad(String prop)
	{
		return propiedadesDB.getProperty(prop);
	}
	
	public void setUser(String user)
	{
		propiedadesDB.put("user", user);
	}
	
	public void setPassword(String pass)
	{
		propiedadesDB.put("password", pass);
	}
	
	public void setPort(String port)
	{
		propiedadesDB.put("port", port);
	}
	
	public void setHost(String host)
	{
		propiedadesDB.put("host", host);
	}
	
	public Properties getPropiedades()
	{
		return this.propiedadesDB;
	}
	
	public void setPropiedad(String prop, String valor)
	{
		this.propiedadesDB.put(prop, valor);
	}
}
