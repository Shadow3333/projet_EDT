package business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Student;
import business.model.users.Teacher;

/**
 * This class test Manager
 * IDao injected in Manager is mocked
 * @author DUBUIS Michael
 *
 */
public class ManagerTest {
	/*
	 * IDao is mocked for not use DataBase
	 */
	private static IDao dao;
	
	/*
	 * Class to test
	 */
	private static Manager manager;
	
	/*
	 * Variables for tests
	 */
	private static AbstractUser student = new Student();
	private static AbstractUser teacher = new Teacher();
	private static AbstractUser admin = new Admin();
	private static AbstractUser newStudent = new Student();
	private static AbstractUser newTeacher = new Teacher();
	private static AbstractUser newAdmin = new Admin();
	private static AbstractUser incompleteStudent = new Student();
	private static AbstractUser incompleteTeacher = new Teacher();
	private static AbstractUser incompleteAdmin = new Admin();
	
	
	@BeforeClass
	public static void init() throws DaoException {
		//
		dao = Mockito.mock(IDao.class);
		// Initialization variables
		student.setEmail("student@projet_edt.com");
		student.setPassword("student");
		teacher.setEmail("teacher@projet_edt.com");
		teacher.setPassword("teacher");
		admin.setEmail("admin@projet_edt.com");
		admin.setPassword("admin");

		newStudent.setEmail("newStudent@projet_edt.com");
		newStudent.setPassword("newStudent");
		newTeacher.setEmail("newTeacher@projet_edt.com");
		newTeacher.setPassword("newTeacher");
		newAdmin.setEmail("newAdmin@projet_edt.com");
		newAdmin.setPassword("newAdmin");
		
		// Mock comportement
		
		// find with email
		Mockito.when(dao.find(
				AbstractUser.class, student.getEmail())
			).thenReturn((AbstractUser) student);
		Mockito.when(
				dao.find(AbstractUser.class, teacher.getEmail())
			).thenReturn((AbstractUser) teacher);
		Mockito.when(
				dao.find(AbstractUser.class, admin.getEmail())
			).thenReturn((AbstractUser) admin);
		Mockito.when(
				dao.find(AbstractUser.class, "absente@projet_edt.com")
			).thenReturn((AbstractUser) null);
		Mockito.when(
				dao.find(Student.class, student.getEmail())
			).thenReturn((Student) student);
		Mockito.when(
				dao.find(Teacher.class, teacher.getEmail())
			).thenReturn((Teacher) teacher);
		Mockito.when(
				dao.find(Admin.class, admin.getEmail())
			).thenReturn((Admin) admin);
		Mockito.when(
				dao.find(Student.class, "absente@projet_edt.com")
			).thenReturn((Student) null);
		Mockito.when(
				dao.find(Teacher.class, "absente@projet_edt.com")
			).thenReturn((Teacher) null);
		Mockito.when(
				dao.find(Admin.class, "absente@projet_edt.com")
			).thenReturn((Admin) null);
		
		// findAll
		List<AbstractUser> listAbstract = new ArrayList<AbstractUser>();
		listAbstract.add(student);
		listAbstract.add(teacher);
		listAbstract.add(admin);
		Mockito.when(dao.findAll(AbstractUser.class)).thenReturn(listAbstract);
		List<Student> listStudent = new ArrayList<Student>();
		listStudent.add((Student) student);
		Mockito.when(dao.findAll(Student.class)).thenReturn(listStudent);
		List<Teacher> listTeacher = new ArrayList<Teacher>();
		listTeacher.add((Teacher) teacher);
		Mockito.when(dao.findAll(Teacher.class)).thenReturn(listTeacher);
		List<Admin> listAdmin = new ArrayList<Admin>();
		listAdmin.add((Admin) admin);
		Mockito.when(dao.findAll(Admin.class)).thenReturn(listAdmin);
		
		// add incomplete User
		Mockito.doNothing().when(dao).save(newAdmin);
		Mockito.doNothing().when(dao).save(newStudent);
		Mockito.doNothing().when(dao).save(newTeacher);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteAdmin);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteStudent);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteTeacher);
		Mockito.doThrow(new DaoException()).when(dao).save(admin);
		Mockito.doThrow(new DaoException()).when(dao).save(student);
		Mockito.doThrow(new DaoException()).when(dao).save(teacher);
		Mockito.doNothing().when(dao).remove(admin);
		Mockito.doNothing().when(dao).remove(student);
		Mockito.doNothing().when(dao).remove(teacher);
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, admin.getEmail());
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, student.getEmail());
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, teacher.getEmail());

		Mockito.doThrow(new DaoException()).when(dao).remove(newAdmin);
		Mockito.doThrow(new DaoException()).when(dao).remove(newStudent);
		Mockito.doThrow(new DaoException()).when(dao).remove(newTeacher);
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newAdmin.getEmail());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newStudent.getEmail());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newTeacher.getEmail());
		
		Mockito.doNothing().when(dao).flush();
		// Manager to test with mocked doa injection
		manager = new Manager(dao);
	}
	
	/**
	 * Test a login method with an existent AbstractUser
	 * Student, Teacher and Admin
	 * @throws IllegalAccessException
	 * @throws DaoException 
	 */
	@Test
	public void LoginWithExistent() throws IllegalAccessException, DaoException {
		assertTrue(manager.login(student.getEmail(), "student"));
		manager.logout();
		assertTrue(manager.login(teacher.getEmail(), "teacher"));
		manager.logout();
		assertTrue(manager.login(admin.getEmail(), "admin"));
		manager.logout();
	}
	
	/**
	 * Try to connect with wrong password
	 * @throws DaoException 
	 */
	@Test
	public void LoginWithWrongPassword() throws DaoException {
		assertFalse(manager.login(admin.getEmail(), "wrongPwd"));
	}
	
	/**
	 * Test a login method with a non-existent AbstractUser
	 * @throws DaoException 
	 */
	@Test
	public void LoginWithNonExistent() throws DaoException {
		manager.login("absente@projet_edt.com", "password");
	}
	
	/**
	 * Try to logout without logged user
	 * @throws IllegalAccessException
	 */
	@Test(expected = IllegalAccessException.class)
	public void LogoutWithoutLoggedUser() throws IllegalAccessException {
		manager.logout();
	}
	
	/**
	 * Try to connect an user connected with another Manager
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	@Test
	public void loginALoggedUser() throws DaoException, IllegalAccessException {
		Manager anotherManager = new Manager(dao);
		assertTrue(anotherManager.login(admin.getEmail(), "admin"));
		assertFalse(manager.login(admin.getEmail(), "admin"));
		anotherManager.logout();
	}
	
	@Test
	public void getCurrentUserWithUserConnected() throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertEquals(admin, manager.getCurrentUser());
		manager.logout();
	}
	
	/**
	 * Try to add an non-existent user with admin logged
	 * AbstractUser is well formed
	 * @throws DaoException 
	 * @throws IllegalAccessException
	 */
	@Test
	public void addNonExistentUserWithAdminLogged() throws DaoException, IllegalAccessException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertTrue(manager.addUser(newStudent));
		assertTrue(manager.addUser(newTeacher));
		assertTrue(manager.addUser(newAdmin));
		manager.logout();
	}
	
	/**
	 * Try to add an non-existent user with teacher logged
	 * AbstractUser is well formed
	 * @throws DaoException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithTeacherLogged() throws DaoException, IllegalAccessException {
		assertTrue(manager.login(teacher.getEmail(), "teacher"));
		try {
			manager.addUser(newStudent);
		} finally {
			manager.logout();
		}
	}

	/**
	 * Try to add an non-existent user with student logged
	 * AbstractUser is well formed
	 * @throws DaoException 
	 * @throws IllegalAccessException 
	 */
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithStudentLogged() throws DaoException, IllegalAccessException {
		assertTrue(manager.login(student.getEmail(), "student"));
		try {
			manager.addUser(newStudent);
		} finally {
			manager.logout();
		}
	}

	/**
	 * Try to add an incomplete user with admin logged
	 * AbstractUser is well formed
	 * @throws DaoException 
	 * @throws IllegalAccessException
	 */
	@Test
	public void addIncompleteUserWithAdminLogged() throws IllegalAccessException, DaoException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertFalse(manager.addUser(incompleteStudent));
		assertFalse(manager.addUser(incompleteTeacher));
		assertFalse(manager.addUser(incompleteAdmin));
		manager.logout();
	}
	
	/**
	 * Try to add an existent user with admin logged
	 * AbstractUser is well formed
	 * @throws DaoException 
	 * @throws IllegalAccessException
	 */
	@Test
	public void addExistentUserWithAdminLogged() throws IllegalAccessException, DaoException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertFalse(manager.addUser(student));
		assertFalse(manager.addUser(teacher));
		assertFalse(manager.addUser(admin));
		manager.logout();
	}
	
	/**
	 * Try to add an non-existent user with admin logged
	 * AbstractUser is well formed
	 * @throws IllegalAccessException
	 */
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithoutLoggedUser() throws IllegalAccessException {
		manager.addUser(newStudent);
	}
	
	/**
	 * Try to remove an existent user with admin logged
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	@Test
	public void removeUserWithAdminLogged() throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.removeUser(student.getEmail()));
		assertTrue(manager.removeUser(teacher.getEmail()));
		// Test don't remove admin cause tested in another test
		manager.logout();
	}
	
	/**
	 * Try to remove an existent user with teacher logged
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithTeacherLogged() throws DaoException, IllegalAccessException {
		try {
			manager.login(teacher.getEmail(), "teacher");
			manager.removeUser(student.getEmail());
		} finally {
			manager.logout();
		}
	}

	/**
	 * Try to remove an existent user with student logged
	 * @throws DaoException
	 * @throws IllegalAccessException
	 */
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithStudentLogged() throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.removeUser(teacher.getEmail());
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void removeUserLogged() throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		manager.removeUser(admin.getEmail());
		assertNull(manager.getCurrentUser());
		manager.login(student.getEmail(), "student");
		manager.removeUser(student.getEmail());
		assertNull(manager.getCurrentUser());
		manager.login(teacher.getEmail(), "teacher");
		manager.removeUser(teacher.getEmail());
		assertNull(manager.getCurrentUser());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithoutUserLogged() throws IllegalAccessException {
		manager.removeUser(student.getEmail());
	}
	
	@Test
	public void removeNonExistentUserWithAdminLogged() throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.removeUser(newAdmin.getEmail()));
		assertFalse(manager.removeUser(newStudent.getEmail()));
		assertFalse(manager.removeUser(newTeacher.getEmail()));
	}
}
