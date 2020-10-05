package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dto.DomicilioDTO;
import dto.PersonaDTO;
import dto.TipoDTO;

public class PersonaDAOSQL implements PersonaDAO
{
	private static final String insert = "INSERT INTO contactos(id, nombre, telefono, correo, idTipo, fecha_cumple, pais, provincia, localidad, calle, altura, tipo_domicilio, piso, dpto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM contactos WHERE id = ?";
	private static final String readall = "SELECT * FROM contactos";
	private static final String readTipo = "SELECT * FROM tipos WHERE id = ?";
	private static final String update = "UPDATE contactos SET nombre=?, telefono=?, correo=?, idTipo=?, fecha_cumple=?, pais=?, provincia=?, localidad=?, calle=?, altura=?, tipo_domicilio=?, piso=?, dpto=? WHERE id = ?";

	public boolean insert(PersonaDTO persona)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			
			statement.setInt(1, persona.getIdPersona());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getTelefono());
			statement.setString(4, persona.getCorreo());
			
			if(persona.getTipo_contacto() == null) 
				statement.setNull(5, Types.INTEGER);
			else 
				statement.setInt(5, persona.getTipo_contacto().getId());
			
			statement.setString(6, persona.getFecha_cumple());
			
			DomicilioDTO domicilio = persona.getDomicilio();
			statement.setString(7, domicilio.getPais());
			statement.setString(8, domicilio.getProvincia());
			statement.setString(9, domicilio.getLocalidad());
			statement.setString(10, domicilio.getCalle());
			statement.setString(11, domicilio.getAltura());
			statement.setString(12, domicilio.getTipo());
			statement.setString(13, domicilio.getPiso());
			statement.setString(14, domicilio.getDpto());

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
	
	public boolean delete(PersonaDTO persona_a_eliminar)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(persona_a_eliminar.getIdPersona()));
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
	
	public boolean update(PersonaDTO persona)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, persona.getNombre());
			statement.setString(2, persona.getTelefono());
			statement.setString(3, persona.getCorreo());
			if(persona.getTipo_contacto() == null) 
				statement.setNull(4, Types.INTEGER);
			else 
				statement.setInt(4, persona.getTipo_contacto().getId());
			statement.setString(5, persona.getFecha_cumple());
			
			DomicilioDTO domicilio = persona.getDomicilio();
			statement.setString(6, domicilio.getPais());
			statement.setString(7, domicilio.getProvincia());
			statement.setString(8, domicilio.getLocalidad());
			statement.setString(9, domicilio.getCalle());
			statement.setString(10, domicilio.getAltura());
			statement.setString(11, domicilio.getTipo());
			statement.setString(12, domicilio.getPiso());
			statement.setString(13, domicilio.getDpto());
			
			statement.setInt(14, persona.getIdPersona());

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
	
	public List<PersonaDTO> readAll()
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PersonaDTO> personas = new ArrayList<PersonaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				personas.add(getPersonaDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return personas;
	}
	
	private PersonaDTO getPersonaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("id");
		String nombre = resultSet.getString("nombre");
		String tel = resultSet.getString("telefono");
		String correo = resultSet.getString("correo");	
		String cumple = resultSet.getString("fecha_Cumple");
		
		TipoDTO tipo = getTipoDTO(resultSet.getInt("idTipo"));
		
		String pais = resultSet.getString("pais");
		String provincia = resultSet.getString("provincia");
		String localidad = resultSet.getString("localidad");
		String calle = resultSet.getString("calle");
		String altura = resultSet.getString("altura");
		String tipoDomicilio = resultSet.getString("tipo_domicilio");
		String piso = resultSet.getString("piso");
		String dpto = resultSet.getString("dpto");
		
		DomicilioDTO domicilio = new DomicilioDTO(pais, provincia, localidad, calle, altura, tipoDomicilio, piso, dpto);
		
		return new PersonaDTO(id, nombre, tel, correo, tipo, cumple, domicilio);
	}
	
	private TipoDTO getTipoDTO(int id) 
	{
		System.out.println("id:" + id);
		PreparedStatement statement;
		ResultSet resultSet;
		TipoDTO tipo = null;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readTipo);
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) 
			{
				int idTipo = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				tipo = new TipoDTO(idTipo, nombre);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return tipo;
	}
	
}
