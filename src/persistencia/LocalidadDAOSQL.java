package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalidadDTO;
import dto.ProvinciaDTO;

public class LocalidadDAOSQL implements LocalidadDAO
{
	private static final String insert = "INSERT INTO localidades (id, idProvincia, nombre) VALUES (?, ?, ?)";
	private static final String delete = "DELETE FROM localidades WHERE id = ?";
	private static final String update = "UPDATE localidades SET nombre = ? WHERE id = ?";
	private static final String select = "SELECT * FROM localidades WHERE idProvincia = ?";

	public boolean insert(LocalidadDTO localidad) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			
			statement.setInt(1, localidad.getId());
			statement.setInt(2, localidad.getIdProvincia());
			statement.setString(3, localidad.getNombre());

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

	public boolean delete(LocalidadDTO localidad_a_eliminar) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(localidad_a_eliminar.getId()));
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

	public boolean update(LocalidadDTO localidad_a_actualizar) 
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, localidad_a_actualizar.getNombre());
			statement.setInt(2, localidad_a_actualizar.getId());

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

	public List<LocalidadDTO> selectLocalidadesFrom(ProvinciaDTO provincia) 
	{
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setInt(1, provincia.getId());
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				localidades.add(getProvinciaDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}

	private LocalidadDTO getProvinciaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		int idProvincia = resultSet.getInt("idProvincia");
		
		return new LocalidadDTO(id, nombre, idProvincia);
	}
}
