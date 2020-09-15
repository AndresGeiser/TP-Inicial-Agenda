package persistencia;


public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	
	public RegionesDAO createRegionesDAO();
}
