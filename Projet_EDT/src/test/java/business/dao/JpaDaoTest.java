package business.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import business.dao.jpa.JpaDao;
import business.dao.jpa.JpaSearchSettings;
import business.manager.AbstractManager;
import business.model.Courses;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
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
	
	@Test
	public void testSaveAndFind1True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user);
		
		assertTrue(dao.find(AbstractUser.class, "aaa@aaa.com")
				.getEmail().equals("aaa@aaa.com"));
	}
	
	@Test(expected=DaoException.class)
	public void testSave1False() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user);
		dao.save(user);
	}
	
	@Test
	public void testSaveAndFind2True() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		user1.setEmail("aaa@aaa.com");
		user1.setPassword("aaaaa");
		user2.setEmail("bbb@bbb.com");
		user2.setPassword("bbbbb");
		dao.save(user1, user2);
		
		AbstractUser[] users = dao.find(AbstractUser.class, 
				"aaa@aaa.com", "bbb@bbb.com"); 
		
		assertTrue(users.length == 2);
		assertTrue(users[0].getEmail().equals("aaa@aaa.com"));
		assertTrue(users[0].getEmail().equals("bbb@bbb.com"));
	}
	
	@Test(expected=DaoException.class)
	public void testSave2False() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user, user);
	}
	
	@Test
	public void testFindAll() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		AbstractUser user3 = new Admin();
		user1.setEmail("aaa@aaa.com");
		user1.setPassword("aaaaa");
		user2.setEmail("bbb@bbb.com");
		user2.setPassword("bbbbb");
		user3.setEmail("ccc@ccc.com");
		user3.setPassword("ccccc");
		
		dao.save(user1, user2, user3);
		
		List<AbstractUser> users = dao.findAll(AbstractUser.class);
		
		assertTrue(users.size() == 3);
		assertTrue(users.get(0).getEmail().equals("aaa@aaa.com"));
		assertTrue(users.get(1).getEmail().equals("bbb@bbb.com"));
		assertTrue(users.get(2).getEmail().equals("ccc@ccc.com"));
	}
	
	/*
	 * Test update methods
	 */
	
	@Test
	public void testUpdateTrue() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user);
		
		user.setFirstName("Dupond");
		
		dao.update(user);
		
		AbstractUser user2 = dao.find(AbstractUser.class, user.getEmail());
		
		assertTrue(user.getFirstName().equals(user2.getFirstName()));
	}
	
	@Test
	public void testUpdate2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		user2.setEmail("bbb@bbb.com");
		user2.setPassword("bbbbb");
		dao.save(user, user2);
		
		user.setFirstName("Dupond");
		user2.setFirstName("Dupont");
		
		dao.update(user, user2);
		
		AbstractUser newUser = dao.find(AbstractUser.class, user.getEmail());
		AbstractUser newUser2 = dao.find(AbstractUser.class, user2.getEmail());
		
		assertTrue(user.getFirstName().equals(newUser.getFirstName()));
		assertTrue(user.getFirstName().equals(newUser2.getFirstName()));
	}
	
	@Test(expected = DaoException.class)
	public void testUpdate1False() throws DaoException {
		AbstractUser user = new Admin();
		
		dao.update(user);
	}
	
	@Test(expected = DaoException.class)
	public void testUpdate2False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		
		dao.update(user, user2);
	}
	
	/*
	 * Test remove methods
	 */
	
	@Test
	public void testRemove1True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		dao.save(user);
		
		dao.remove(user);
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	@Test
	public void testRemove2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.com");
		user.setPassword("aaaaa");
		user2.setEmail("bbb@bbb.com");
		user.setPassword("bbbbb");
		dao.save(user, user2);
		
		dao.remove(user, user2);
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
		assertTrue(dao.find(AbstractUser.class, user2.getEmail()) == null);
	}
	
	@Test(expected = DaoException.class)
	public void testRemove1False() throws DaoException {
		AbstractUser user = new Admin();
		
		dao.remove(user);
	}
	
	@Test(expected = DaoException.class)
	public void testRemove2False() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		
		dao.remove(user, user2);
	}
	
	@Test
	public void testRemoveByIdTrue() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.aaa");
		user.setPassword("aaaaa");
		dao.save(user);
		
		dao.removeById(AbstractUser.class, "aaa@aaa.aaa");
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	@Test
	public void testRemoveByIdsTrue() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.aaa");
		user.setPassword("aaaaa");
		user2.setEmail("bbb@bbb.bbb");
		user2.setPassword("bbbbb");
		dao.save(user, user2);

		dao.removeByIds(AbstractUser.class, "aaa@aaa.aaa", "bbb@bbb.bbb");
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
		assertTrue(dao.find(AbstractUser.class, user2.getEmail()) == null);
	}
	
	@Test(expected = DaoException.class)
	public void testRemoveByIdFalse() throws DaoException {
		dao.removeById(AbstractUser.class, "aaa@aaa.aaa");
	}
	
	@Test(expected = DaoException.class)
	public void testRemoveByIdsFalse() throws DaoException {
		dao.removeByIds(AbstractUser.class, "aaa@aaa.aaa", "bbb@bbb.bbb");
	}
	
	/*
	 * Test search methods
	 */
	
	@Test
	public void testSearch1True () throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa.aaa@aaa");
		user.setPassword("aaaaa");
		user.setFirstName("Dupond");
		dao.save(user);
		
		List<AbstractUser> users = dao.search(AbstractUser.class, "FirstName", "Dupond");
		
		assertTrue(users.get(0).getEmail().equals(("aaa.aaa@aaa")));
	}
	
	@Test
	public void testSearch2True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa.aaa@aaa");
		user.setPassword("aaaaa");
		user.setFirstName("Dupond");
		dao.save(user);
		
		JpaSearchSettings settings = new JpaSearchSettings();
		settings.withKeyToField("firstName", "Dupond");
		settings.withoutKeyToField("lastName", "Geoffrey", "Paul");
		List<AbstractUser> users = dao.search(AbstractUser.class, settings);
		
		assertTrue(users.get(0).getEmail().equals(("aaa.aaa@aaa")));
	}
	
	@Test
	public void testSearch1False() throws DaoException {
		assertTrue(dao.search(AbstractUser.class, "FirstName", "Dupond").equals(null));
	}
	
	@Test
	public void testSearch2False() throws DaoException {
		JpaSearchSettings settings = new JpaSearchSettings();
		settings.withKeyToField("firstName", "Dupond");
		settings.withoutKeyToField("lastName", "Geoffrey", "Paul");
		assertTrue(dao.search(AbstractUser.class, settings).equals(null));
	}
	
	/*
	 * Test other methods
	 */
	
	@Test
	public void testIsAttachedTrue() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa.aaa@aaa");
		user.setPassword("aaaaa");
		user.setFirstName("Dupond");
		dao.save(user);
		
		assertTrue(dao.isAttached(user) == false);
	}
	
	@Test
	public void testIsAttachedFalse() throws DaoException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(null);
		EntityManager em = emf.createEntityManager();
		
		AbstractUser user = new Admin();
		user.setEmail("aaa.aaa@aaa");
		user.setPassword("aaaaa");
		user.setFirstName("Dupond");
		dao.save(user);
		
		em.detach(user);
		em.flush();
		
		assertTrue(dao.isAttached(user) == false);
	}
  
	@Test
	public void testIsEntityTrue() {
		assertTrue(dao.isEntity(Courses.class) == true);
	}
	
	@Test
	public void testIsEntityFalse() {
		assertTrue(dao.isEntity(AbstractManager.class) == false);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
