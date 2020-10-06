package persistencia;

import java.util.List;

import dto.LocalidadDTO;
import dto.ProvinciaDTO;

public interface LocalidadDAO 
{
	public boolean insert(LocalidadDTO provincia);

	public boolean delete(LocalidadDTO provincia_a_eliminar);
	
	public boolean update(LocalidadDTO provincia_a_actualizar);
	
	public List<LocalidadDTO> selectLocalidadesFrom(ProvinciaDTO provincia);
}
