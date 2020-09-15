package modelo;

import java.util.List;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;
import persistencia.DAOAbstractFactory;
import persistencia.RegionesDAO;

public class Regiones {
	
	private RegionesDAO regiones;
	
	public Regiones(DAOAbstractFactory metodo_persistencia)
	{
		this.regiones = metodo_persistencia.createRegionesDAO();
	}
	
	public List<PaisDTO> obtenerPaises() {
		return this.regiones.getPaises();
	}

	public List<ProvinciaDTO> obtenerProvincias(PaisDTO pais) {
		return this.regiones.getProvincias(pais);
	}
	
	public List<LocalidadDTO> obtenerLocalidades(ProvinciaDTO provincia) {
		return this.regiones.getLocalidades(provincia);
	}
	
}
