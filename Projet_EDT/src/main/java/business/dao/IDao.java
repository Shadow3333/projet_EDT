package business.dao;

import java.io.Serializable;
import java.util.List;

/**
 * This interface must be implemented for find, update, delete and search entities
 * @author DUBUIS Michael
 *
 */
public interface IDao {
	// Create
	/**
	 * Create one entity
	 * @param entity
	 * @throws DaoException - if entity already exist
	 */
	public void save(Object entity) throws DaoException;

	/**
	 * Create all entities
	 * @param entities
	 * @throws DaoException - if one of entities already exist
	 */
	public void save(Object... entities) throws DaoException;

	//Read
	/**
	 * Find an entity with this ID
	 * @param type - Entity's class
	 * @param id - Entity's ID
	 * @return
	 */
	public <T> T find(Class<T> type, Serializable id) throws DaoException;

	/**
	 * Find all entities with these IDs
	 * @param type - Entities' class
	 * @param ids - Array of entities' IDs
	 * @return
	 */
	public <T> T[] find(Class<T> type, Serializable... ids) throws DaoException;

	/**
	 * 
	 * @param type - Entities' class
	 * @return
	 */
	public <T> List<T> findAll(Class<T> type) throws DaoException;

	// Update
	/**
	 * Update an existent entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	public void update(Object entity) throws DaoException;
	
	/**
	 * Update existent entities
	 * @param entities
	 * @throws DaoException
	 */
	public void update(Object... entities) throws DaoException;
	
	/**
	 * Sends to the database any changes made to the EntityManager
	 */
    public void flush();
	
	// Delete
	/**
	 * Delete an entity if exists
	 * @param entity
	 * @return The suppression state parameter entity
	 * @return True if saved, false else.
	 */
	public void remove(Object entity) throws DaoException;

	/**
	 * Delete all entities if exists
	 * @param entities
	 * @return Array of boolean
	 * @return Each boolean matches the suppression state parameter entities
	 * @return True if saved, false else.
	 */
	public void remove(Object... entities) throws DaoException;

	/**
	 * Delete an entity with this ID
	 * @param type - Entity's class
	 * @param id - Entity's ID
	 * @return The suppression state parameter entity
	 * @return True if saved, false else.
	 */
	public <T> void removeById(Class<T> type, Serializable id) throws DaoException;

	/**
	 * Delete all entities with these ID
	 * @param type - Entities' class
	 * @param ids - Entities' IDs
	 * @return Array of boolean
	 * @return Each boolean matches the suppression state parameter entities
	 * @return True if saved, false else.
	 */
	public <T> void removeByIds(Class<T> type, Serializable... ids) throws DaoException;
	
	// Search
	/**
	 * Looking for entities depending on the keyword relative to the field
	 * @param type
	 * @param field
	 * @param key
	 * @return
	 */
	public <T> List<T> search(Class<T> type, String field, String key)
			throws DaoException;
	
	/**
	 * Looking for entities depending to the fields parameter
	 * @param type
	 * @param setting
	 * @return
	 */
	public <T> List<T> search(Class<T> type, ISearchSettings setting)
			throws DaoException;
	
	// Other
	/**
	 * Define if entity is attached to entity manager
	 * @param entity
	 * @return True if attached, false else.
	 */
    public boolean isAttached(Object entity);
    
    /**
     * Upgrade to entities from the database
     * @param entities
     */
    public void refresh(Object... entities) throws DaoException;
}
