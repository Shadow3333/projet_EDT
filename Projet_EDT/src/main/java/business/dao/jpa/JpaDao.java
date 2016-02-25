package business.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import business.dao.DaoException;
import business.dao.IDao;

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
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> rootQuery = criteriaQuery.from(type);
		criteriaQuery.select(rootQuery);
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return query.getResultList();
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

	public <T> List<T> search(Class<T> type, String field, String key)
			throws DaoException {
		SearchSettings setting = new SearchSettings();
		setting.withKeyToField(field, key);
		return search(type, setting);
	}

	public <T> List<T> search(Class<T> type, SearchSettings setting)
			throws DaoException {
		/*
		 * READ BEFORE MODIFICATIONS :
		 * This method is sensible, care save a backup
		 */
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> rootQuery = criteriaQuery.from(type);
		Metamodel metamodel = em.getMetamodel();
		EntityType<T> type_ = metamodel.entity(type);

		// get the predicate (where clause)
		Predicate predicate = criteriaQuery.getRestriction();
		// Put "with clauses"
		for(Entry<String, List<Object>> e : setting.getWith().entrySet()) {
			predicate = 
					criteriaBuilder.and(
							predicate,
							rootQuery.get(
									type_.getSingularAttribute(e.getKey()))
							.in(e.getValue()));
		}
		// Put "without clauses"
		for(Entry<String, List<Object>> e : setting.getWithout().entrySet()) {
			predicate = 
					criteriaBuilder.and(
							predicate,
							criteriaBuilder.not(
									rootQuery.get(
											type_.getSingularAttribute(
													e.getKey()))
									.in(e.getValue())));
		}
		// Define where with predicate created
		criteriaQuery.where(predicate);
		// Define type of query
		criteriaQuery.select(rootQuery);
		// Create the query
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		return query.getResultList();
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
