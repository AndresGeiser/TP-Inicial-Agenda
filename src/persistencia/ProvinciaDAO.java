package persistencia;

import java.util.List;

import dto.PaisDTO;
import dto.ProvinciaDTO;

public interface ProvinciaDAO 
{
	public boolean insert(ProvinciaDTO provincia);

	public boolean delete(ProvinciaDTO provincia_a_eliminar);
	
	public boolean update(ProvinciaDTO provincia_a_actualizar);
	
	public List<ProvinciaDTO> selectProvinciasFrom(PaisDTO pais);
}
