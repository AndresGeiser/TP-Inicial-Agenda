package dto;

public class DomicilioDTO 
{
	private PaisDTO pais;
	private ProvinciaDTO provincia;
	private LocalidadDTO localidad;
	private String calle;
	private String altura;
	private String tipo;
	private String piso;
	private String dpto;
	
	public DomicilioDTO(PaisDTO pais, ProvinciaDTO provincia, LocalidadDTO localidad, String calle, String altura, String tipo, String piso, String dpto)
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
	

	public PaisDTO getPais() 
	{
		return pais;
	}
	
	public void setPais(PaisDTO pais) 
	{
		this.pais = pais;
	}
	
	public ProvinciaDTO getProvincia() 
	{
		return provincia;
	}
	
	public void setProvincia(ProvinciaDTO provincia) 
	{
		this.provincia = provincia;
	}
	
	public LocalidadDTO getLocalidad() 
	{
		return localidad;
	}
	
	public void setLocalidad(LocalidadDTO localidad) 
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
