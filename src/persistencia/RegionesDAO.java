package persistencia;

import java.util.List;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;

public interface RegionesDAO 
{
	public List<PaisDTO> getPaises();
	
	public List<ProvinciaDTO> getProvincias(PaisDTO pais);
	
	public List<LocalidadDTO> getLocalidades(ProvinciaDTO provincia);
}
