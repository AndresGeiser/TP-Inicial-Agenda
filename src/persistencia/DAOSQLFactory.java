/**
 * 
 */
package persistencia;

import persistencia.DAOAbstractFactory;
import persistencia.PersonaDAO;

public class DAOSQLFactory implements DAOAbstractFactory 
{
	/* (non-Javadoc)
	 * @see persistencia.dao.interfaz.DAOAbstractFactory#createPersonaDAO()
	 */
	public PersonaDAO createPersonaDAO() 
	{
		return new PersonaDAOSQL();
	}

	public TipoDAO createTipoDAO() 
	{
		return new TipoDAOSQL();
	}
	
	public PaisDAO createPaisDAO() 
	{
		return new PaisDAOSQL();
	}
	
	public ProvinciaDAO createProvinciaDAO() 
	{
		return new ProvinciaDAOSQL();
	}
	
	public LocalidadDAO createLocalidadDAO() 
	{
		return new LocalidadDAOSQL();
	}

}
