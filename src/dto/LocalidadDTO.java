package dto;

public class LocalidadDTO {
	
	private int id;
	private String nombre;
	private int idProvincia;
	
	public LocalidadDTO(int id, String nombre, int idProvincia) 
	{
		this.id = id;
		this.nombre = nombre;
		this.idProvincia = idProvincia;
	}
	
	public int getId() 
	{
		return id;
	}

	public String getNombre() 
	{
		return nombre;
	}
	
	public int getIdProvincia() 
	{
		return idProvincia;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public void setIdProvincia(int idProvincia) 
	{
		this.idProvincia = idProvincia;
	}
	
}
