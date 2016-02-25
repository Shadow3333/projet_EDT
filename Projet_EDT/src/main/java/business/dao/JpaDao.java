package business.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Implementation of <code>IDao</code>
 * @author DUBUIS Michael
 *
 */
public class JpaDao implements IDao {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public JpaDao() {
		emf = Persistence.createEntityManagerFactory("Projet_EDT");
		em = emf.createEntityManager();
	}
	
	public boolean save(Object entity) {
		em.persist(entity);
		return true;
	}

	public boolean[] save(Object... entities) {
		boolean[] booleans = new boolean[entities.length];
		for(int i = 0 ; i < entities.length ; i++) {
			booleans[i] = true;
			em.persist(entities[i]);
		}
		return booleans;
	}

	public <T> T find(Class<T> type, Serializable id) {
		return em.find(type, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] find(Class<T> type, Serializable... ids) {
		Object[] t = new Object[ids.length];
		for(int i = 0 ; i < ids.length ; i++) {
			t[i] = em.find(type, ids[i]);
		}
		return (T[]) t;
	}

	public <T> List<T> findAll(Class<T> type) {
		try {
			return em.createNamedQuery("findAll", type).getResultList();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void flush() {
		em.getTransaction().begin();
		em.flush();
		em.getTransaction().commit();
	}

	public boolean remove(Object entity) {
		em.remove(entity);
		return true;
	}

	public boolean[] remove(Object... entities) {
		boolean[] booleans = new boolean[entities.length];
		for(int i = 0 ; i < entities.length ; i++) {
			booleans[i] = true;
			em.remove(entities[i]);
		}
		return booleans;
	}

	public <T> boolean removeById(Class<T> type, Serializable id) {
		T t = find(type, id);
		if(t == null) {
			return false;
		}
		return remove(t);
	}

	public <T> boolean[] removeByIds(Class<T> type, Serializable... ids) {
		boolean[] booleans = new boolean[ids.length];
		for(int i = 0 ; i < ids.length ; i++) {
			T t = find(type, ids[i]);
			if(t == null) {
				booleans[i] = false;
			} else {
				booleans[i] = remove(t);
			}
		}
		return booleans;
	}

	public boolean isAttached(Object entity) {
		return !em.contains(entity);
	}

	public void refresh(Object... entities) {
		for(int i = 0 ; i < entities.length ; i++){
			em.refresh(entities[i]);
		}
	}

}
