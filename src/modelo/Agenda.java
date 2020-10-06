package modelo;

import java.util.List;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoDTO;
import persistencia.DAOAbstractFactory;
import persistencia.LocalidadDAO;
import persistencia.PaisDAO;
import persistencia.PersonaDAO;
import persistencia.ProvinciaDAO;
import persistencia.TipoDAO;


public class Agenda 
{
	private PersonaDAO persona;
	private TipoDAO tipo;
	private PaisDAO pais;
	private ProvinciaDAO provincia;
	private LocalidadDAO localidad;
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		persona = metodo_persistencia.createPersonaDAO();
		tipo = metodo_persistencia.createTipoDAO();
		pais = metodo_persistencia.createPaisDAO();
		provincia = metodo_persistencia.createProvinciaDAO();
		localidad = metodo_persistencia.createLocalidadDAO();
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
	
	
	/************************/
	/* 		ABM Pais 	*/
	/************************/
	
	public void agregarPais(PaisDTO nuevoPais) 
	{
		pais.insert(nuevoPais);
	}
	
	public void editarPais(PaisDTO pais_a_editar) 
	{
		pais.update(pais_a_editar);
	}
	
	public void borrarPais(PaisDTO pais_a_borrar) 
	{
		//Borramos el domicilio de las personas que coinciden con el pais a eliminar para no romper con la restriccion en la db
		List<PersonaDTO> personas = persona.readAll();
	
		int idPaisEliminar = pais_a_borrar.getId();
		DomicilioDTO domicilio;
		PaisDTO pais;
		int idPais;
		for (PersonaDTO persona : personas) 
		{
			domicilio = persona.getDomicilio();
			pais = domicilio.getPais();
			if(pais != null) 
			{
				idPais = pais.getId();
				if(idPais == idPaisEliminar)
				{
					persona.setDomicilio(new DomicilioDTO(null, null, null, "", "", "", "", ""));
					this.persona.update(persona);
				}
			}
		}
		
		//Eliminamos el pais 
		this.pais.delete(pais_a_borrar);
	}
	
	public List<PaisDTO> obtenerPaises() 
	{
		return pais.readAll();
	}
	
	
	/************************/
	/* 		ABM Provincia 	*/
	/************************/
	
	public void agregarProvincia(ProvinciaDTO nuevaProvincia) 
	{
		provincia.insert(nuevaProvincia);
	}
	
	public void editarProvincia(ProvinciaDTO provincia_a_editar) 
	{
		provincia.update(provincia_a_editar);
	}
	
	public void borrarProvincia(ProvinciaDTO provincia_a_borrar) 
	{
		//Borramos el domicilio de las personas que coinciden con el pais a eliminar para no romper con la restriccion en la db
		List<PersonaDTO> personas = persona.readAll();
	
		int idProvinciaEliminar = provincia_a_borrar.getId();
		DomicilioDTO domicilio;
		ProvinciaDTO provincia;
		int idProvincia;
		for (PersonaDTO persona : personas) 
		{
			domicilio = persona.getDomicilio();
			provincia = domicilio.getProvincia();
			if(provincia != null) 
			{
				idProvincia = provincia.getId();
				if(idProvincia == idProvinciaEliminar)
				{
					persona.setDomicilio(new DomicilioDTO(null, null, null, "", "", "", "", ""));
					this.persona.update(persona);
				}
			}
		}
		
		//Eliminamos la provincia 
		this.provincia.delete(provincia_a_borrar);
	}
	
	public List<ProvinciaDTO> obtenerProvincias(PaisDTO pais)
	{
		return provincia.selectProvinciasFrom(pais);
	}
	
	
	/************************/
	/* 		ABM Localidad 	*/
	/************************/
	
	public void agregarLocalidad(LocalidadDTO nuevaLocalidad) 
	{
		localidad.insert(nuevaLocalidad);
	}
	
	public void editarLocalidad(LocalidadDTO localidad_a_editar) 
	{
		localidad.update(localidad_a_editar);
	}
	
	public void borrarLocalidad(LocalidadDTO localidad_a_borrar) 
	{
		//Borramos el domicilio de las personas que coinciden con el pais a eliminar para no romper con la restriccion en la db
		List<PersonaDTO> personas = persona.readAll();
	
		int idLocalidadEliminar = localidad_a_borrar.getId();
		DomicilioDTO domicilio;
		LocalidadDTO localidad;
		int idLocalidad;
		for (PersonaDTO persona : personas) 
		{
			domicilio = persona.getDomicilio();
			localidad = domicilio.getLocalidad();
			if(localidad != null) 
			{
				idLocalidad = localidad.getId();
				if(idLocalidad == idLocalidadEliminar)
				{
					persona.setDomicilio(new DomicilioDTO(null, null, null, "", "", "", "", ""));
					this.persona.update(persona);
				}
			}
		}
		
		//Eliminamos la localidad 
		this.localidad.delete(localidad_a_borrar);
	}
	
	public List<LocalidadDTO> obtenerLocalidades(ProvinciaDTO provincia)
	{
		return localidad.selectLocalidadesFrom(provincia);
	}
	
	
	
}
