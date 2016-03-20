package business.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import business.dao.jpa.JpaDao;
import business.dao.jpa.JpaSearchSettings;
import business.model.Courses;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
 * This file contains all methods to test JpaDao
 * 
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 *
 */
public class JpaDaoTest {

	/*
	 * Class to test
	 */
	//@Autowired
	private static JpaDao dao;
	
	/**
	 * Init dao for test
	 */
	@BeforeClass
	public static void init() {
		try {
			dao = new JpaDao();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * Test save and find methods
	 */
	
	/**
	 * test save and find functions 
	 * with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testSaveAndFind1True() throws DaoException {
		AbstractUser user = new Admin();
		try {
			user.setEmail("testSaveAndFind1True@aaa.com");
			user.setPassword("aaaaa");
			dao.save(user);
			
			assertTrue(dao.find(AbstractUser.class, "testSaveAndFind1True@aaa.com")
					.getEmail().equals("testSaveAndFind1True@aaa.com"));
			
		} finally {
			dao.remove(user);
		}
	}
	
	/**
	 * test save function 
	 * with a false result
	 * @throws DaoException
	 */
	@Test(expected=DaoException.class)
	public void testSave1False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		try {
			user.setEmail("testSave1False@aaa.com");
			user.setPassword("aaaaa");
			user2.setEmail(user.getEmail());
			user2.setPassword("aaaaa");
			dao.save(user);
			dao.save(user2);
		} finally {
			dao.removeById(AbstractUser.class, "testSave1False@aaa.com");
		}
	}
	
	/**
	 * test 2nd version of save and find function
	 * with true result
	 * @throws DaoException
	 */
	@Test
	public void testSaveAndFind2True() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		try {
			user1.setEmail("testSaveAndFind2True@aaa.com");
			user1.setPassword("aaaaa");
			user2.setEmail("testSaveAndFind2True@bbb.com");
			user2.setPassword("bbbbb");
			dao.save(user1, user2);
			
			AbstractUser[] users = dao.find(AbstractUser.class, 
					user1.getEmail(), user2.getEmail()); 
			
			assertTrue(users.length == 2);
			assertTrue(users[0].getEmail().equals(user1.getEmail()));
			assertTrue(users[1].getEmail().equals(user2.getEmail()));
		} finally {
			dao.remove(user1, user2);
		}
	}
	
	/**
	 * test 2nd version of save
	 * with a false result
	 * @throws DaoException
	 */
	@Test(expected=DaoException.class)
	public void testSave2False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		try {
			user.setEmail("testSave2False@aaa.com");
			user.setPassword("aaaaa");
			user2.setEmail(user.getEmail());
			user2.setPassword("aaaaa");
			dao.save(user, user2);
			
		} finally {
			dao.removeById(AbstractUser.class, "testSave2False@aaa.com");
		}
	}
	
	/**
	 * test FindAll function
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testFindAll() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		AbstractUser user3 = new Admin();
		try {
			user1.setEmail("testFindAll@aaa.com");
			user1.setPassword("aaaaa");
			user2.setEmail("testFindAll@bbb.com");
			user2.setPassword("bbbbb");
			user3.setEmail("testFindAll@ccc.com");
			user3.setPassword("ccccc");
			
			dao.save(user1, user2, user3);
			
			List<AbstractUser> users = dao.findAll(AbstractUser.class);
			
			assertTrue(users.contains(user1));
			assertTrue(users.contains(user2));
			assertTrue(users.contains(user3));
		} finally {
			dao.remove(user1,user2,user3);
		}
	}
	
	/*
	 * Test update methods
	 */
	
	/**
	 * test update function
	 * with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testUpdateTrue() throws DaoException {
		AbstractUser user = new Admin();
		try {
			user.setEmail("testUpdateTrue@aaa.com");
			user.setPassword("aaaaa");
			dao.save(user);
			
			
			user.setFirstName("Dupond");
			
			dao.update(user);
			
			
			AbstractUser user2 = dao.find(AbstractUser.class, user.getEmail());
			
			assertTrue(user.getFirstName().equals(user2.getFirstName()));
		} finally {
			dao.remove(user);
		}
	}
	
	/**
	 * test 2nd version of update
	 * with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testUpdate2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		try {
			user.setEmail("testUpdate2True@aaa.com");
			user.setPassword("aaaaa");
			user2.setEmail("testUpdate2True@bbb.com");
			user2.setPassword("bbbbb");
			dao.save(user, user2);
			
			
			user.setFirstName("Dupond");
			user2.setFirstName("Dupont");
			
			dao.update(user, user2);
			
			
			AbstractUser newUser = dao.find(AbstractUser.class, user.getEmail());
			AbstractUser newUser2 = dao.find(AbstractUser.class, user2.getEmail());
			
			assertTrue(user.getFirstName().equals(newUser.getFirstName()));
			assertTrue(user2.getFirstName().equals(newUser2.getFirstName()));
		} finally {
			dao.remove(user);
			dao.remove(user2);
		}
	}
	
	/**
	 * test update function with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testUpdate1False() throws DaoException {
		AbstractUser user = new Admin();
		
		dao.update(user);
	}
	
	/**
	 * test update function with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testUpdate2False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		
		dao.update(user, user2);
	}
	
	/*
	 * Test remove methods
	 */
	
	/**
	 * test remove function with a true result
	 * @throws DaoException
	 */
	@Test
	public void testRemove1True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("testRemove1True@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user);
		
		
		dao.remove(user);
		
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	/**
	 * test 2nd version of remove with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testRemove2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("testRemove2True@aaa.com");
		user.setPassword("aaaaa");
		user2.setEmail("testRemove2True@bbb.com");
		user2.setPassword("bbbbb");
		dao.save(user, user2);
		
		
		dao.remove(user, user2);
		
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
		assertTrue(dao.find(AbstractUser.class, user2.getEmail()) == null);
	}
	
	/**
	 * test remove with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testRemove1False() throws DaoException {
		AbstractUser user = new Admin();
		
		dao.remove(user);
	}
	
	/**
	 * test 2nd version of remove with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testRemove2False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		
		dao.remove(user, user2);
	}
	
	/**
	 * test removeById with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testRemoveByIdTrue() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("testRemoveByIdTrue@aaa.aaa");
		user.setPassword("aaaaa");
		dao.save(user);
		
		
		dao.removeById(AbstractUser.class, "testRemoveByIdTrue@aaa.aaa");
		
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	/**
	 * test removeByIds with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testRemoveByIdsTrue() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("testRemoveByIdsTrue@aaa.aaa");
		user.setPassword("aaaaa");
		user2.setEmail("testRemoveByIdsTrue@bbb.bbb");
		user2.setPassword("bbbbb");
		dao.save(user, user2);
		

		dao.removeByIds(AbstractUser.class, "testRemoveByIdsTrue@aaa.aaa", "testRemoveByIdsTrue@bbb.bbb");
		
		
		assertNull(dao.find(AbstractUser.class, user.getEmail()));
		assertNull(dao.find(AbstractUser.class, user2.getEmail()));
	}
	
	/**
	 * test removeById with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testRemoveByIdFalse() throws DaoException {
		dao.removeById(AbstractUser.class, "aaa@aaa.aaa");
	}
	
	/**
	 * test removeByIds with an user not in database
	 * 
	 * @throws DaoException
	 */
	@Test(expected = DaoException.class)
	public void testRemoveByIdsFalse() throws DaoException {
		dao.removeByIds(AbstractUser.class, "aaa@aaa.aaa", "bbb@bbb.bbb");
	}
	
	/*
	 * Test search methods
	 */
	
	/**
	 * test search with a true result
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testSearch1True () throws DaoException {
		AbstractUser user = new Admin();
		try {
			user.setEmail("testSearch1True@aaa");
			user.setPassword("aaaaa");
			user.setFirstName("Dupond");
			dao.save(user);
			
			
			List<AbstractUser> users = dao.search(AbstractUser.class, "FirstName", "Dupond");
			
			assertTrue(users.get(0).getEmail().equals(("testSearch1True@aaa")));
		} finally {
			dao.remove(user);
		}
	}
	
	/**
	 * test 2nd version of search with a true result
	 * @throws DaoException
	 */
	@Test
	public void testSearch2True() throws DaoException {
		AbstractUser user = new Admin();
		try {
			user.setEmail("testSearch2True@aaa");
			user.setPassword("aaaaa");
			user.setFirstName("Dupond");
			dao.save(user);
			
			JpaSearchSettings settings = new JpaSearchSettings();
			settings.withKeyToField("FirstName", "Dupond");
			settings.withoutKeyToField("LastName", "Geoffrey", "Paul");
			List<AbstractUser> users = dao.search(AbstractUser.class, settings);
			
			assertTrue(users.get(0).getEmail().equals(("testSearch2True@aaa")));
		} finally {
			dao.remove(user);
		}
	}
	
	/**
	 * test search with a user not in database
	 * 
	 * @throws DaoException
	 */
	@Test
	public void testSearch1False() throws DaoException {
		assertNull(dao.search(AbstractUser.class, "FirstName", "Dupond"));
	}
	
	/**
	 * test 2nd version of search with a user not in database
	 * @throws DaoException
	 */
	@Test
	public void testSearch2False() throws DaoException {
		JpaSearchSettings settings = new JpaSearchSettings();
		settings.withKeyToField("firstName", "Dupond");
		settings.withoutKeyToField("lastName", "Geoffrey", "Paul");
		assertNull(dao.search(AbstractUser.class, settings));
	}
	
	/*
	 * Test other methods
	 */
	
	
	/**
	 * test isAttached function with a true result
	 */
	@Test
	public void testIsAttachedTrue() throws DaoException {
		/*AbstractUser user = new Admin();
		user.setEmail("testIsAttachedTrue@aaa");
		user.setPassword("aaaaa");
		user.setFirstName("Dupond");
		dao.save(user);
		
		
		assertTrue(dao.isAttached(user) == false);
		FIXME JpaDao.isAttached(user) doesn't work like that.
		*/
	}
	
	/**
	 * test isAttached function with a false result
	 */
	@Test
	public void testIsAttachedFalse() throws DaoException {
		/*EntityManagerFactory emf = Persistence.createEntityManagerFactory(null);
		EntityManager em = emf.createEntityManager();
		
		AbstractUser user = new Admin();
		try {
			user.setEmail("testIsAttachedFalse@aaa");
			user.setPassword("aaaaa");
			user.setFirstName("Dupond");
			dao.save(user);
			
			em.detach(user);
			em.flush();
			
			assertTrue(dao.isAttached(user) == false);
		} finally {
			dao.remove(user);
		}
		FIXME JpaDao.isAttached(user) doesn't work like that.
		*/
	}
  
	/**
	 * test isEntity function with a true result
	 */
	@Test
	public void testIsEntityTrue() {
		assertTrue(dao.isEntity(Courses.class));
	}
	
	/**
	 * test isEntity function with a false result
	 */
	@Test
	public void testIsEntityFalse() {
		assertFalse(dao.isEntity(String.class));
	}
}
