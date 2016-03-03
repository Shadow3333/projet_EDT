package business.manager;

import java.io.Serializable;
import java.lang.reflect.Type;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.Courses;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
 * @author DUBUIS Michael
 *
 */
@SuppressWarnings("unchecked")
public abstract class AbstractManager<T extends Object> {
	protected IDao dao = null;
	
	private Class<? extends Type> type;
	
	/**
	 * Empty constructor
	 */
	private AbstractManager() {
		type = this.getClass().getGenericSuperclass().getClass();
		if(!dao.isEntity(type)) {
			System.exit(1);
		}
	}
	
	/**
	 * Constructor with <code>IDao</code> parameter
	 * @param dao
	 */
	public AbstractManager(IDao dao) {
		this();
		this.dao = dao;
	}
	
	/**
	 * User passed in parameter can Save this type of Entity ?
	 * @param user
	 * @return
	 */
	public abstract boolean canSave(AbstractUser user);
	
	/**
	 * User passed in parameter can Remove this type of Entity ?
	 * @param user
	 * @return
	 */
	public abstract boolean canRemove(AbstractUser user);
	
	/**
	 * User passed in parameter can FindAll Entities of this type ?
	 * @param user
	 * @return
	 */
	public abstract boolean canFindAll(AbstractUser user);
	
	/**
	 * User passed in parameter can Find Entity of this type ?
	 * @param user
	 * @return
	 */
	public abstract boolean canFind(AbstractUser user);
	
	/**
	 * User passed in parameter can update this type of Entity ?
	 * @param user
	 * @return
	 */
	public abstract boolean canUpdate(AbstractUser user);
	
	/**
	 * Find entity
	 * @param user
	 * @param id
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	public T find(AbstractUser user, Serializable id) throws DaoException, IllegalAccessException {
		if(!canFind(user)) {
			throw new IllegalAccessException();
		}
		return (T) dao.find(type, id);
	}
	
	/**
	 * Find all entities
	 * @param user
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	public T findAll(AbstractUser user)
			throws DaoException, IllegalAccessException {
		if(!canFindAll(user)) {
			throw new IllegalAccessException();
		}
		return (T) dao.findAll(type);
	}
	
	/**
	 * Save new entity
	 * @param user
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean save(AbstractUser user, T entity)
			throws IllegalAccessException {
		if(!canSave(user)) {
			throw new IllegalAccessException();
		}
		try {
			dao.save(entity);
			dao.flush();
			return true;
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Remove entity
	 * @param user
	 * @param entity
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	public boolean remove(AbstractUser user, T entity)
			throws DaoException, IllegalAccessException {
		if(!canRemove(user)) {
			throw new IllegalAccessException();
		}
		try {
			dao.remove(entity);
			dao.flush();
			return true;
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Remove entity with id
	 * @param user
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean remove(AbstractUser user, Serializable id)
			throws IllegalAccessException {
		if(!canRemove(user)) {
			throw new IllegalAccessException();
		}
		try {
			dao.removeById(type, id);
			dao.flush();
			return true;
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Update entity
	 * @param user
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean update(AbstractUser user, T entity) 
			throws IllegalAccessException{
		if(!canUpdate(user)) {
			throw new IllegalAccessException();
		}
		try {
			entity = (T) dao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
