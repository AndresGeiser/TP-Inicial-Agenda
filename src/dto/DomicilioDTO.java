package dto;

public class DomicilioDTO 
{
	private String pais;
	private String provincia;
	private String localidad;
	private String calle;
	private String altura;
	private String tipo;
	private String piso;
	private String dpto;
	
	public DomicilioDTO(String pais, String provincia, String localidad, String calle, String altura, String tipo, String piso, String dpto)
	{
		this.pais = pais;
		this.provincia = provincia;
		this.localidad = localidad;
		this.calle = calle;
		this.altura = altura;
		this.tipo = tipo;
		this.piso = piso;
		this.dpto = dpto;
	}
	

	public String getPais() 
	{
		return pais;
	}
	
	public void setPais(String pais) 
	{
		this.pais = pais;
	}
	
	public String getProvincia() 
	{
		return provincia;
	}
	
	public void setProvincia(String provincia) 
	{
		this.provincia = provincia;
	}
	
	public String getLocalidad() 
	{
		return localidad;
	}
	
	public void setLocalidad(String localidad) 
	{
		this.localidad = localidad;
	}

	public String getCalle() 
	{
		return this.calle ;
	}
	
	public void setCalle(String calle) 
	{
		this.calle = calle;
	}
	
	public String getAltura() 
	{
		return this.altura;
	}
	
	public void setAltura(String altura) 
	{
		this.altura = altura;
	}
	
	public String getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}
	
	public String getPiso() 
	{
		return this.piso;
	}
	
	public void setPiso(String piso) 
	{
		this.piso = piso;
	}
	
	public String getDpto() 
	{
		return this.dpto;
	}

	public void setDpto(String dpto) 
	{
		this.dpto = dpto;
	}
	
}
