package dto;

public class PersonaDTO 
{
	
	private int id;
	private String nombre;
	private String telefono;
	private String correo;
	private String tipo_contacto;
	private String fecha_cumple;
	private DomicilioDTO domicilio; 
	
	public PersonaDTO(int id, String nombre, String telefono, String correo, String tipo_contacto, String fecha_cumple, DomicilioDTO domicilio)
	{
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.tipo_contacto = tipo_contacto;
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

	public String getTipo_contacto() 
	{
		return tipo_contacto;
	}

	public void setTipo_contacto(String tipo_contacto) 
	{
		this.tipo_contacto = tipo_contacto;
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
