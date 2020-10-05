package persistencia;

public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	
	public RegionesDAO createRegionesDAO();

	public TipoDAO createTipoDAO();
}
