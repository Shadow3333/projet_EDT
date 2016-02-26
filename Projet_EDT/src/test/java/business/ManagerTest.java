package business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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
	private static AbstractUser s = new Student();
	private static AbstractUser t = new Teacher();
	private static AbstractUser a = new Admin();
	
	
	@BeforeClass
	public static void init() {
		//
		dao = Mockito.mock(IDao.class);
		// Mock comportement
		s.setEmail("student@projet_edt.com");
		s.setPassword("student");
		t.setEmail("teacher@projet_edt.com");
		t.setPassword("teacher");
		a.setEmail("admin@projet_edt.com");
		a.setPassword("admin");
		// find with email
		Mockito.when(dao.find(
				AbstractUser.class, s.getEmail())
			).thenReturn((AbstractUser) s);
		Mockito.when(
				dao.find(AbstractUser.class, t.getEmail())
			).thenReturn((AbstractUser) t);
		Mockito.when(
				dao.find(AbstractUser.class, a.getEmail())
			).thenReturn((AbstractUser) a);
		Mockito.when(
				dao.find(AbstractUser.class, "absente@projet_edt.com")
			).thenReturn((AbstractUser) null);
		Mockito.when(
				dao.find(Student.class, s.getEmail())
			).thenReturn((Student) s);
		Mockito.when(
				dao.find(Teacher.class, t.getEmail())
			).thenReturn((Teacher) t);
		Mockito.when(
				dao.find(Admin.class, a.getEmail())
			).thenReturn((Admin) a);
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
		listAbstract.add(s);
		listAbstract.add(t);
		listAbstract.add(a);
		Mockito.when(dao.findAll(AbstractUser.class)).thenReturn(listAbstract);
		List<Student> listStudent = new ArrayList<Student>();
		listStudent.add((Student) s);
		Mockito.when(dao.findAll(Student.class)).thenReturn(listStudent);
		List<Teacher> listTeacher = new ArrayList<Teacher>();
		listTeacher.add((Teacher) t);
		Mockito.when(dao.findAll(Teacher.class)).thenReturn(listTeacher);
		List<Admin> listAdmin = new ArrayList<Admin>();
		listAdmin.add((Admin) a);
		Mockito.when(dao.findAll(Admin.class)).thenReturn(listAdmin);

		// Manager to test with mocked doa injection
		manager = new Manager(dao);
	}
	
	@Test
	public void LoginWithExistant() throws IllegalAccessException {
		assertTrue(manager.login(s.getEmail(), "student"));
		manager.logout();
		assertTrue(manager.login(t.getEmail(), "teacher"));
		manager.logout();
		assertTrue(manager.login(a.getEmail(), "admin"));
		manager.logout();
	}
	
	@Test
	public void LoginWithUnexistante() {
		assertFalse(manager.login("absente@projet_edt.com", "password"));
	}
	
	@Test(expected = IllegalAccessException.class)
	public void LogoutWithoutLoggedUser() throws IllegalAccessException {
		manager.logout();
	}
}
