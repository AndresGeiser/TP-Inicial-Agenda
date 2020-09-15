package persistencia;

import java.util.List;

import dto.PersonaDTO;

public interface PersonaDAO 
{
	
	public boolean insert(PersonaDTO persona);

	public boolean delete(PersonaDTO persona_a_eliminar);
	
	public boolean update(PersonaDTO persona_a_actualizar);
	
	public List<PersonaDTO> readAll();
}
