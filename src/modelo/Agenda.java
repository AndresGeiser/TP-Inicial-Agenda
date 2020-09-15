package modelo;

import java.util.List;
import dto.PersonaDTO;
import persistencia.DAOAbstractFactory;
import persistencia.PersonaDAO;


public class Agenda 
{
	private PersonaDAO persona;	
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		this.persona = metodo_persistencia.createPersonaDAO();
	}
	
	public void agregarPersona(PersonaDTO nuevaPersona)
	{
		this.persona.insert(nuevaPersona);
	}

	public void borrarPersona(PersonaDTO persona_a_eliminar) 
	{
		this.persona.delete(persona_a_eliminar);
	}
	
	public void editarPersona(PersonaDTO persona_a_actualizar)
	{
		this.persona.update(persona_a_actualizar);
	}
	
	public List<PersonaDTO> obtenerPersonas()
	{
		return this.persona.readAll();		
	}
	
}
