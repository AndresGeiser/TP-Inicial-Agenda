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

	
	public RegionesDAO createRegionesDAO() {
		
		return new RegionesDAOSQL();
	}


	public TipoDAO createTipoDAO() 
	{
		return new TipoDAOSQL();
	}

}
