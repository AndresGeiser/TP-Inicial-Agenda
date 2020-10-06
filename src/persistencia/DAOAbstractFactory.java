package persistencia;

public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	
	public TipoDAO createTipoDAO();
	
	public PaisDAO createPaisDAO();
	
	public ProvinciaDAO createProvinciaDAO();
	
	public LocalidadDAO createLocalidadDAO();
}
