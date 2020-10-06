package dto;

public class ProvinciaDTO {
	
	private int id;
	private String nombre;
	private int idPais;
	
	public ProvinciaDTO(int id, String nombre, int idPais) 
	{
		this.id = id;
		this.nombre = nombre;
		this.idPais = idPais;
	}
	
	public int getId() 
	{
		return id;
	}

	public String getNombre() 
	{
		return nombre;
	}
	
	public int getIdPais() 
	{
		return idPais;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public void setIdPais(int idPais) 
	{
		this.idPais = idPais;
	}
	
}
