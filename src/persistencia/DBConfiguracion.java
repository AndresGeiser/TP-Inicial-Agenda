package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfiguracion 
{

	private Properties propiedadesDB;
	
	
	public DBConfiguracion(String usuario, String contra, String ip, String puerto) //Se le pueden pasar los parametros para configurar la conexion
	{
	    System.setProperty("Jdbc.drivers", "com.mysql.jdbc.Driver");
	   
		//Levantamos el archivo de propiedades, si no existe creamos el directorio
		propiedadesDB = new Properties();
		try 
		{
			propiedadesDB.load(new FileInputStream("DB/configDB.properties")); //Arreglar esto a carpeta Otros
		} 
		catch (IOException e) 
		{
			File folder = new File("DB");
			if (!folder.exists()) 
			{
				folder.mkdir();
			}
			
			//Este metodo va a hacer los cambios en la base antes de guardar las configuraciones en el archivo
			setearConfig(usuario, contra, ip, puerto);
			
			//Se crea el archivo con la configuracion inicial
			crearConfig(usuario, contra, ip, puerto);
		}
		
		
		
	}
	
	//La idea es que existan una serie de .batch o comandos que accedan a la consola mysql/ejecute un script en xampp/mysql/lib/mysql.exe o mysqlAdmin.exe
    private void setearConfig(String usuario,String contra,String ip,String puerto) 
    {
    	//Por ej: 	 cd C:\Xampp\mysql\lib
    	//			 mysql.exe -u root
    	//			 update user set username..
    	//			 flush privileges
    	//
    	//La contraseña y el usuario se pueden setear con este metodo
    	//El puerto no, hay que modificar una linea en el archivo my.ini ubicado en cd C:\Xampp\mysql\lib
    	//Para la ip lo mismo (pendiente investigar)
    	
    	
    	
    	
    	//Despues de hacer la configuracion, comunicar al usuario que los cambios se van a aplicar al reiniciar la aplicación
    	//IMPORTANTE: REINICIAR EL SERVICIO MYSQL SI SE CAMBIA EL PUERTO O LA IP (Xampp trae un .batch para reiniciarlo)
	}

	//Este metodo podria recibir como argumentos los que setee el usuario - ojo este metodo solo se usa para la primera vez! (o debería)
	private void crearConfig(String usuario, String contra, String ip, String puerto)
	{
		//Seteamos las propiedades de la conexion (clave,valor)
		 propiedadesDB.put("prefix", "jdbc:mysql://");
	     propiedadesDB.put("base","/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); //Evita errores de fechas
		 propiedadesDB.put("port", puerto); 
		 propiedadesDB.put("ip", ip ); //Ojo, no necesariamente deben ser numeros, puede ser localhost
	     propiedadesDB.put("user", usuario );
		 propiedadesDB.put("password", contra);
		 propiedadesDB.put("configCreada","1");
		
		 
		 
		 FileOutputStream arch = null;	   	    
			try 
			{
			  arch=new FileOutputStream("DB/configDB.properties"); //Arreglar esto a carpeta Otros
			  propiedadesDB.store(arch, "Fichero de Propiedades de la conexion");
			}
			catch(IOException ioe) 
				{ioe.printStackTrace();}
		
	}
	
	//Devuelve el String "1" si ya se configuro la conexion al menos una vez, puede usarse para mostrar la configuracion solo la primera vez
	public String configEstaCreada()
	{
		return propiedadesDB.getProperty("configCreada");
	}
	
	public String getPrefix()
	{
		return propiedadesDB.getProperty("prefix");
	}
	
	public String getBase()
	{
		return propiedadesDB.getProperty("base");
	}
	
	public String getPuerto()
	{
		return propiedadesDB.getProperty("port");
	}
	
	public String getUser()
	{
		return propiedadesDB.getProperty("user");
	}
	
	public String getPassword()
	{
		return propiedadesDB.getProperty("password");
	}
	
	public void setUser(String user)
	{
		propiedadesDB.setProperty("user", user);
	}
	
	public void setPassword(String pass)
	{
		propiedadesDB.setProperty("password", pass);
	}
	
	public void setPort(String port)
	{
		propiedadesDB.setProperty("port", port);
	}
	
	public void setIP(String ip)
	{
		propiedadesDB.setProperty("ip", ip);
	}
	
}
