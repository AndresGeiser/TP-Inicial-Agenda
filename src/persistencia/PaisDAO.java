package persistencia;

import java.util.List;

import dto.PaisDTO;

public interface PaisDAO 
{
	public boolean insert(PaisDTO pais);

	public boolean delete(PaisDTO pais_a_eliminar);
	
	public boolean update(PaisDTO pais_a_actualizar);
	
	public List<PaisDTO> readAll();
}
