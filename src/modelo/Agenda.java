package modelo;

import java.util.List;
import dto.PersonaDTO;
import dto.TipoDTO;
import persistencia.DAOAbstractFactory;
import persistencia.PersonaDAO;
import persistencia.TipoDAO;


public class Agenda 
{
	private PersonaDAO persona;
	private TipoDAO tipo;
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		persona = metodo_persistencia.createPersonaDAO();
		tipo = metodo_persistencia.createTipoDAO();
	}
	
	/************************/
	/* 	ABM Persona 		*/
	/************************/
	public void agregarPersona(PersonaDTO nuevaPersona)
	{
		persona.insert(nuevaPersona);
	}

	public void borrarPersona(PersonaDTO persona_a_eliminar) 
	{
		persona.delete(persona_a_eliminar);
	}
	
	public void editarPersona(PersonaDTO persona_a_actualizar)
	{
		persona.update(persona_a_actualizar);
	}
	
	public List<PersonaDTO> obtenerPersonas()
	{
		return persona.readAll();		
	}
	
	
	/************************/
	/* ABM Tipo de contacto */
	/************************/
	public void agregarTipoDeContacto(TipoDTO nuevoTipo) 
	{
		tipo.insert(nuevoTipo);
	}
	
	public void borrarTipoDeContacto(TipoDTO tipo_a_eliminar) 
	{
		//Actualizamos el tipo de las personas que coinciden con el tipo a eliminar para no romper con la restriccion en la db
		List<PersonaDTO> personas = persona.readAll();
	
		String nombreTipoEliminar = tipo_a_eliminar.getNombre();
		TipoDTO tipo;
		String nombreTipo;
		for (PersonaDTO persona : personas) 
		{
			tipo = persona.getTipo_contacto();
			if(tipo != null) 
			{
				nombreTipo = tipo.getNombre();
				if(nombreTipo.equals(nombreTipoEliminar))
				{
					persona.setTipo_contacto(null);
					this.persona.update(persona);
				}
			}
		}
		
		//Eliminamos el tipo 
		this.tipo.delete(tipo_a_eliminar);
	}
	
	public void editarTipoDeContacto(TipoDTO tipo_a_actualizar) 
	{
		tipo.update(tipo_a_actualizar);
	}
	
	public List<TipoDTO> obtenerTiposDeContacto()
	{
		return tipo.readAll();
	}
	
}
