package dto;

public class PersonaDTO 
{
	private int idPersona;
	private String nombre;
	private String telefono;
	private String correo;
	private String tipo_contacto;
	private String fecha_cumple;
	
	public PersonaDTO(int idPersona, String nombre, String telefono, String correo, String tipo_contacto, String fecha_cumple)
	{
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.tipo_contacto = tipo_contacto;
		this.fecha_cumple = fecha_cumple;
	}
	
	public int getIdPersona() 
	{
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) 
	{
		this.idPersona = idPersona;
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
}
