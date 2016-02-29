package business;

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
	//private static AbstractUser incompleteStudent = new Student();
	//private static AbstractUser incompleteTeacher = new Teacher();
	//private static AbstractUser incompleteAdmin = new Admin();
	
	
	@BeforeClass
	public static void init() throws DaoException {
		//
		dao = Mockito.mock(IDao.class);
		// Mock comportement
		student.setEmail("student@projet_edt.com");
		student.setPassword("student");
		teacher.setEmail("teacher@projet_edt.com");
		teacher.setPassword("teacher");
		admin.setEmail("admin@projet_edt.com");
		admin.setPassword("admin");
		
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
	 * Try to add an non-existent user with admin logged
	 * AbstractUser is well formed
	 */
	@Test
	public void addNonExistentUserWithAdminLogged() {
		
	}
}
