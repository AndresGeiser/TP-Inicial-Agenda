package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalidadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;

public class RegionesDAOSQL implements RegionesDAO {
	
	private static final String selectPaises = "SELECT * FROM paises";
	private static final String selectProvincias = "SELECT * FROM provincias WHERE idPais = ?";
	private static final String selectLocalidades = "SELECT * FROM localidades WHERE idProvincia = ?";

	public List<PaisDTO> getPaises() 
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PaisDTO> paises = new ArrayList<PaisDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(selectPaises);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				paises.add(getPaisDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return paises;
	}

	public List<ProvinciaDTO> getProvincias(PaisDTO pais) 
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(selectProvincias);
			statement.setInt(1, pais.getId());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				provincias.add(getProvinciaDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return provincias;
	}

	public List<LocalidadDTO> getLocalidades(ProvinciaDTO provincia) 
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(selectLocalidades);
			statement.setInt(1, provincia.getId());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				localidades.add(getLocalidadDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}
	
	private PaisDTO getPaisDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		
		return new PaisDTO(id, nombre);
	}
	
	private ProvinciaDTO getProvinciaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		int idPais = resultSet.getInt("idPais");
		
		return new ProvinciaDTO(id, nombre, idPais);
	}
	
	private LocalidadDTO getLocalidadDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		int idProvincia = resultSet.getInt("idProvincia");
		
		return new LocalidadDTO(id, nombre, idProvincia);
	}
	
}
