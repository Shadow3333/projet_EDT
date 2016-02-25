package business.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao {
	// Create
	/**
	 * Create one entity
	 * @param entity
	 * @return The state of backup data parameter entity
	 * @return True if saved, false else.
	 */
	public boolean save(Object entity);

	/**
	 * Create all entities
	 * @param entities
	 * @return Array of boolean
	 * @return Each boolean matches the state of backup parameter entities
	 * @return True if saved, false else.
	 */
	public boolean[] save(Object... entities);

	//Read
	/**
	 * Find an entity with this ID
	 * @param type - Entity's class
	 * @param id - Entity's ID
	 * @return
	 */
	public <T> T find(Class<T> type, Serializable id);

	/**
	 * Find all entities with these IDs
	 * @param type - Entities' class
	 * @param ids - Array of entities' IDs
	 * @return
	 */
	public <T> T[] find(Class<T> type, Serializable... ids);

	/**
	 * 
	 * @param type - Entities' class
	 * @return
	 */
	public <T> List<T> findAll(Class<T> type);

	// Update
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
	public boolean remove(Object entity);

	/**
	 * Delete all entities if exists
	 * @param entities
	 * @return Array of boolean
	 * @return Each boolean matches the suppression state parameter entities
	 * @return True if saved, false else.
	 */
	public boolean[] remove(Object... entities);

	/**
	 * Delete an entity with this ID
	 * @param type - Entity's class
	 * @param id - Entity's ID
	 * @return The suppression state parameter entity
	 * @return True if saved, false else.
	 */
	public <T> boolean removeById(Class<T> type, Serializable id);

	/**
	 * Delete all entities with these ID
	 * @param type - Entities' class
	 * @param ids - Entities' IDs
	 * @return Array of boolean
	 * @return Each boolean matches the suppression state parameter entities
	 * @return True if saved, false else.
	 */
	public <T> boolean[] removeByIds(Class<T> type, Serializable... ids);
	
	// Search
	// Not yet
	
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
    public void refresh(Object... entities);
}
