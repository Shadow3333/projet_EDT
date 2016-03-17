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

import org.springframework.stereotype.Component;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.ISearchSettings;

/**
 * Implementation of <code>IDao</code>
 * @author DUBUIS Michael
 *
 */
@Component
public class JpaDao implements IDao {
	private EntityManagerFactory emf;
	private EntityManager em;

	public JpaDao() {
		this("Projet_EDT");
	}
	
	public JpaDao(String entityUnit) {
		System.out.print("Creation entity manager factory... ");
		emf = Persistence.createEntityManagerFactory(entityUnit);
		System.out.println("Done");
		System.out.print("Creation entity manager... ");
		em = emf.createEntityManager();
		System.out.println("Done");
	}
	
	public void save(Object entity) throws DaoException {
		try {
			em.persist(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void save(Object... entities) throws DaoException {
		for(Object entity : entities) {
			save(entity);
		}
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
	
	public <T> T update(T entity) throws DaoException {
		try {
			return em.merge(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	public <T> T[] update(T...entities) throws DaoException {
		for(Object entity : entities) {
			entity = update(entity);
		}
		return entities;
	}
	
	public void flush() {
		em.getTransaction().begin();
		em.flush();
		em.getTransaction().commit();
	}

	public void remove(Object entity) throws DaoException {
		try {
			em.remove(entity);
		} catch(Exception e) {
			throw new DaoException(e);
		}
	}

	public void remove(Object... entities) throws DaoException {
		for(Object entity : entities) {
			remove(entity);
		}
	}

	public <T> void removeById(Class<T> type, Serializable id) throws DaoException {
		T t = find(type, id);
		if(t == null) {
			throw new DaoException("Non-existent entity");
		}
		remove(t);
	}

	public <T> void removeByIds(Class<T> type, Serializable... ids) throws DaoException {
		for(Serializable id : ids) {
			removeById(type, id);
		}
	}

	public <T> List<T> search(Class<T> type, String field, String key)
			throws DaoException {
		JpaSearchSettings setting = new JpaSearchSettings();
		setting.withKeyToField(field, key);
		return search(type, setting);
	}

	public <T> List<T> search(Class<T> type, ISearchSettings setting)
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
	
	public <T> boolean isEntity(Class<T> type) {
		return em.getMetamodel().getEntities().contains(type);
	}
}
