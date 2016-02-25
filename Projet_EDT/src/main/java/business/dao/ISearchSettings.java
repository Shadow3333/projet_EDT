package business.dao;

import java.util.List;
import java.util.Map;

import business.dao.jpa.IllogicArgumentException;

/**
 * This interface must be implemented for search function from <code>IDao</code> 
 * @author DUBUIS Michael
 *
 */
public interface ISearchSettings {
	/**
	 * Add all key to found in a field
	 * @param field
	 * @param keys
	 * @throws IllogicArgumentException
	 */
	public void withKeyToField(String field, Object... keys)
			throws DaoException;

	/**
	 * Add all key to exclude in a field
	 * @param field
	 * @param keys
	 * @throws IllogicArgumentException
	 */
	public void withoutKeyToField(String field, Object... keys)
			throws DaoException;

	/**
	 * @return with fields map
	 */
	public Map<String, List<Object>> getWith();
	
	/**
	 * Reset with fields map
	 */
	public void resetWith();
	
	/**
	 * @return without fields map
	 */
	public Map<String, List<Object>> getWithout();
	
	/**
	 * Reset without fields map
	 */
	public void resetWithout();
	
}
