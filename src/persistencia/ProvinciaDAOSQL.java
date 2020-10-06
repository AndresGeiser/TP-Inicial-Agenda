package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PaisDTO;
import dto.ProvinciaDTO;

public class ProvinciaDAOSQL implements ProvinciaDAO
{
	private static final String insert = "INSERT INTO provincias (id, idPais, nombre) VALUES (?, ?, ?)";
	private static final String delete = "DELETE FROM provincias WHERE id = ?";
	private static final String update = "UPDATE provincias SET nombre = ? WHERE id = ?";
	private static final String select = "SELECT * FROM provincias WHERE idPais = ?";

	public boolean insert(ProvinciaDTO provincia) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			
			statement.setInt(1, provincia.getId());
			statement.setInt(2, provincia.getIdPais());
			statement.setString(3, provincia.getNombre());

			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}

	public boolean delete(ProvinciaDTO provincia_a_eliminar) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(provincia_a_eliminar.getId()));
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isdeleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isdeleteExitoso;
	}

	public boolean update(ProvinciaDTO provincia_a_actualizar) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, provincia_a_actualizar.getNombre());
			statement.setInt(2, provincia_a_actualizar.getId());

			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isUpdateExitoso;
	}

	public List<ProvinciaDTO> selectProvinciasFrom(PaisDTO pais) 
	{
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(select);
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

	private ProvinciaDTO getProvinciaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		int idPais = resultSet.getInt("idPais");
		
		return new ProvinciaDTO(id, nombre, idPais);
	}
}
