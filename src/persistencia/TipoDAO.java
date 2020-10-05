package persistencia;

import java.util.List;
import dto.TipoDTO;

public interface TipoDAO 
{
	public boolean insert(TipoDTO tipo);

	public boolean delete(TipoDTO tipo_a_eliminar);
	
	public boolean update(TipoDTO tipo_a_editar);
	
	public List<TipoDTO> readAll();
}
