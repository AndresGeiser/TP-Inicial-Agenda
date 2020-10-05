package dto;

public class PersonaDTO 
{
	
	private int id;
	private String nombre;
	private String telefono;
	private String correo;
	private String fecha_cumple;
	private TipoDTO tipo;
	private DomicilioDTO domicilio; 
	
	public PersonaDTO(int id, String nombre, String telefono, String correo, TipoDTO tipo, String fecha_cumple, DomicilioDTO domicilio)
	{
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.tipo = tipo;
		this.fecha_cumple = fecha_cumple;
		this.domicilio = domicilio;
	}
	
	public int getIdPersona() 
	{
		return this.id;
	}

	public void setIdPersona(int id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return this.telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getCorreo() 
	{
		return correo;
	}

	public void setCorreo(String correo) 
	{
		this.correo = correo;
	}

	public TipoDTO getTipo_contacto() 
	{
		return tipo;
	}

	public void setTipo_contacto(TipoDTO tipo) 
	{
		this.tipo = tipo;
	}

	public String getFecha_cumple() 
	{
		return fecha_cumple;
	}

	public void setFecha_cumple(String fecha_cumple) 
	{
		this.fecha_cumple = fecha_cumple;
	}
	
	public DomicilioDTO getDomicilio() 
	{
		return domicilio;
	}
	
	public void setDomicilio(DomicilioDTO domicilio) 
	{
		this.domicilio = domicilio;
	}

}
