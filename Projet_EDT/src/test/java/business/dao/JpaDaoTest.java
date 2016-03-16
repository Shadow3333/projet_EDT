package business.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import business.dao.jpa.JpaDao;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
 * @author DUBUIS Michael
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
@TransactionConfiguration(defaultRollback=true)
public class JpaDaoTest {

	/*
	 * Class to test
	 */
	@Autowired
	private static JpaDao dao;
	
	public void init() {
		dao = new JpaDao();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void testSaveAndFind1True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		dao.save(user);
		
		assertTrue(dao.find(AbstractUser.class, "aaa@aaa.com").getEmail() == "aaa@aaa.com");
	}
	
	@Test(expected=DaoException.class)
	public void testSave1False() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		dao.save(user);
		dao.save(user);
	}
	
	@Test
	public void testSaveAndFind2True() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		user1.setEmail("aaa@aaa.com");
		user2.setEmail("bbb@bbb.com");
		dao.save(user1, user2);
		
		AbstractUser[] users = dao.find(AbstractUser.class, "aaa@aaa.com", "bbb@bbb.com"); 
		
		assertTrue(users.length == 2);
		assertTrue(users[0].getEmail() == "aaa@aaa.com");
		assertTrue(users[0].getEmail() == "bbb@bbb.com");
	}
	
	@Test(expected=DaoException.class)
	public void testSave2False() throws DaoException {
		AbstractUser user1 = new Admin();
		user1.setEmail("aaa@aaa.com");
		dao.save(user1, user1);
	}
	
	@Test
	public void testFindAll() throws DaoException {
		AbstractUser user1 = new Admin();
		AbstractUser user2 = new Admin();
		AbstractUser user3 = new Admin();
		user1.setEmail("aaa@aaa.com");
		user2.setEmail("bbb@bbb.com");
		user3.setEmail("ccc@ccc.com");
		
		dao.save(user1, user2, user3);
		
		List<AbstractUser> users = dao.findAll(AbstractUser.class);
		
		assertTrue(users.size() == 3);
		assertTrue(users.get(0).getEmail() == "aaa@aaa.com");
		assertTrue(users.get(1).getEmail() == "bbb@bbb.com");
		assertTrue(users.get(2).getEmail() == "ccc@ccc.com");
	}
	
	@Test
	public void testUpdateTrue() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		dao.save(user);
		
		user.setFirstName("Dupond");
		
		dao.update(user);
		
		AbstractUser user2 = dao.find(AbstractUser.class, user.getEmail());
		
		assertTrue(user.getFirstName() == user2.getFirstName());
	}
	
	@Test
	public void testUpdate2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.com");
		user2.setEmail("bbb@bbb.com");
		dao.save(user);
		dao.save(user2);
		
		user.setFirstName("Dupond");
		user2.setFirstName("Dupont");
		
		dao.update(user, user2);
		
		AbstractUser newUser = dao.find(AbstractUser.class, user.getEmail());
		AbstractUser newUser2 = dao.find(AbstractUser.class, user2.getEmail());
		
		assertTrue(user.getFirstName() == newUser.getFirstName());
		assertTrue(user.getFirstName() == newUser2.getFirstName());
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
	
	@Test
	public void testRemove1True() throws DaoException {
		AbstractUser user = new Admin();
		user.setEmail("aaa@aaa.com");
		dao.save(user);
		
		dao.remove(user);
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	@Test
	public void testRemove2True() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.com");
		user2.setEmail("bbb@bbb.com");
		dao.save(user);
		dao.save(user2);
		
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
		dao.save(user);
		
		dao.removeById(AbstractUser.class, "aaa@aaa.aaa");
		
		assertTrue(dao.find(AbstractUser.class, user.getEmail()) == null);
	}
	
	@Test
	public void testRemoveByIdsTrue() throws DaoException {
		AbstractUser user = new Admin();
		AbstractUser user2 = new Admin();
		user.setEmail("aaa@aaa.aaa");
		user.setEmail("bbb@bbb.bbb");
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
	
	@Test
	public void testSearch1True () {
		
	}
	
/*	
	public <T> List<T> search(Class<T> type, String field, String key)
			throws DaoException;
	
	
	public <T> List<T> search(Class<T> type, ISearchSettings setting)
			throws DaoException;
	
	
    public boolean isAttached(Object entity);
    
  
    public void refresh(Object... entities) throws DaoException;
    
   
    public <T> boolean isEntity(Class<T> type);
    
    */
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
