package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfiguracion 
{

	private Properties propiedadesDB;
	
	
	
	public DBConfiguracion() //Se le pueden pasar los parametros para configurar la conexion
	{
	    System.setProperty("Jdbc.drivers", "com.mysql.jdbc.Driver");
	   
		//Levantamos el archivo de propiedades, si no existe creamos el directorio
		propiedadesDB = new Properties();
		try 
		{
			propiedadesDB.load(new FileInputStream("DB/configDB.properties"));
		} 
		catch (IOException e) 
		{
			File folder = new File("DB");
			if (!folder.exists()) 
			{
				folder.mkdir();
			}
			crearConfig();
		}
		
		
		
	}
	
    //Este metodo podria recibir como argumentos los que setee el usuario
	private void crearConfig()
	{
		//Seteamos las propiedades de la conexion (clave,valor)
	     propiedadesDB.put("url","jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		 propiedadesDB.put("port","3306"); //Aun no utilizado
	     propiedadesDB.put("user","root");
		 propiedadesDB.put("password","");
		
		 
		 
		 FileOutputStream arch = null;	   	    
			try 
			{
			  arch=new FileOutputStream("DB/configDB.properties");
			  propiedadesDB.store(arch, "Fichero de Propiedades de la conexion");
			}
			catch(IOException ioe) 
				{ioe.printStackTrace();}
		
	}
	
	public String getUrl()
	{
		return propiedadesDB.getProperty("url");
	}
	
	public String getUser()
	{
		return propiedadesDB.getProperty("user");
	}
	
	public String getPassword()
	{
		return propiedadesDB.getProperty("password");
	}
	
	
	
	
	
}
