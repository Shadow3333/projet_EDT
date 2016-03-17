package business.manager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import business.dao.DaoException;
import business.dao.IDao;

/**
 * @author DUBUIS Michael
 * @contributor LELIEVRE Romain
 */
@SuppressWarnings("unchecked")
public abstract class AbstractManager<T extends Object> {
	protected IDao dao = null;
	protected Manager manager = null;
	
	protected Class<? extends Type> type;
	
	/**
	 * Empty constructor
	 */
	private AbstractManager() {
		type = (Class<? extends Type>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Constructor with <code>IDao</code> parameter
	 * @param dao
	 */
	public AbstractManager(IDao dao, Manager manager) {
		this();
		this.dao = dao;
		this.manager = manager;
		/*if(!dao.isEntity(type)) {
			System.exit(1);
		}*/
	}
	
	/**
	 * User passed in parameter can Save this type of Entity ?
	 * @param user
	 * @return
	 */
	public boolean canSave() {
		return true;
	}
	
	/**
	 * User passed in parameter can Remove this type of Entity ?
	 * @param user
	 * @return
	 */
	public boolean canRemove() {
		return true;
	}
	
	/**
	 * User passed in parameter can FindAll Entities of this type ?
	 * @param user
	 * @return
	 */
	public boolean canFindAll() {
		return true;
	}
	
	/**
	 * User passed in parameter can Find Entity of this type ?
	 * @param user
	 * @return
	 */
	public boolean canFind() {
		return true;
	}
	
	/**
	 * User passed in parameter can update this type of Entity ?
	 * @param user
	 * @return
	 */
	public boolean canUpdate() {
		return true;
	}
	
	/**
	 * Find entity
	 * @param user
	 * @param id
	 * @return
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	public T find(Serializable id) throws DaoException, IllegalAccessException {
		if(!canFind()) {
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
	public List<T> findAll()
			throws DaoException, IllegalAccessException {
		if(!canFindAll()) {
			throw new IllegalAccessException();
		}
		return (List<T>) dao.findAll(type);
	}
	
	/**
	 * Save new entity
	 * @param user
	 * @param entity
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean save(T entity)
			throws IllegalAccessException {
		if(!canSave()) {
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
	public boolean remove(T entity)
			throws DaoException, IllegalAccessException {
		if(!canRemove()) {
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
	public boolean remove(Serializable id)
			throws IllegalAccessException {
		if(!canRemove()) {
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
	public boolean update(T entity) 
			throws IllegalAccessException{
		if(!canUpdate()) {
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
