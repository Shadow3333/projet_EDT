package business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.EU;
import business.model.EU.LessonType;
import business.model.GroupEU;
import business.model.GroupStudent;
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
	private static EU eu = new EU();
	private static EU newEU = new EU();
	private static EU incompleteEU = new EU();
	private static GroupStudent groupStudentCM = new GroupStudent();
	private static GroupStudent groupStudentTD = new GroupStudent();
	private static GroupStudent groupStudentTP = new GroupStudent();
	private static GroupStudent newGroupStudent = new GroupStudent();
	private static GroupStudent incompleteGroupStudent = new GroupStudent();
	private static GroupEU groupEU = new GroupEU();
	private static GroupEU newGroupEU = new GroupEU();
	private static GroupEU incompleteGroupEU = new GroupEU();
	
	
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
		
		Map<LessonType, Integer> c = new HashMap<LessonType, Integer>();
		c.put(LessonType.CM, 10);
		c.put(LessonType.TD, 10);
		c.put(LessonType.TP, 10);
		c.put(LessonType.Other, 0);
		eu.setId("EU01");
		eu.setName("First EU obligatory");
		eu.setNbCredits(3);
		eu.setNbHours(new HashMap<LessonType, Integer>(c));
		newEU.setId("EU");
		
		List<AbstractUser> listStudentForGroupStudent =
				new ArrayList<AbstractUser>();
		listStudentForGroupStudent.add((Student) student);
		groupStudentCM.setId(1L);
		groupStudentCM.setGroupType(LessonType.CM);
		groupStudentCM.setStudents(listStudentForGroupStudent);
		groupStudentTD.setId(2L);
		groupStudentTD.setGroupType(LessonType.TD);
		groupStudentTD.setStudents(listStudentForGroupStudent);
		groupStudentTP.setId(3L);
		groupStudentTP.setGroupType(LessonType.TP);
		groupStudentTP.setStudents(listStudentForGroupStudent);
		newGroupStudent.setId(4L);
		
		// Mock comportement
		
		/* find */
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
		Mockito.when(
				dao.find(EU.class, eu.getId())
				).thenReturn(eu);
		Mockito.when(
				dao.find(EU.class, newEU.getId())
				).thenThrow(new DaoException());
		Mockito.when(
				dao.find(GroupStudent.class, groupStudentCM.getId())
				).thenReturn(groupStudentCM);
		Mockito.when(
				dao.find(GroupStudent.class, groupStudentTD.getId())
				).thenReturn(groupStudentTD);
		Mockito.when(
				dao.find(GroupStudent.class, groupStudentTP.getId())
				).thenReturn(groupStudentTP);
		Mockito.when(
				dao.find(GroupStudent.class, newGroupStudent.getId())
				).thenThrow(new DaoException());
		
		/* findAll */
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
		List<EU> listEU = new ArrayList<EU>();
		listEU.add(eu);
		Mockito.when(dao.findAll(EU.class)).thenReturn(listEU);
		List<GroupStudent> listGroupStudent = new ArrayList<GroupStudent>();
		listGroupStudent.add(groupStudentCM);
		listGroupStudent.add(groupStudentTD);
		listGroupStudent.add(groupStudentTP);
		Mockito.when(
				dao.findAll(GroupStudent.class)).thenReturn(listGroupStudent);
		
		/* Save */
		Mockito.doNothing().when(dao).save(newAdmin);
		Mockito.doNothing().when(dao).save(newStudent);
		Mockito.doNothing().when(dao).save(newTeacher);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteAdmin);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteStudent);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteTeacher);
		Mockito.doThrow(new DaoException()).when(dao).save(admin);
		Mockito.doThrow(new DaoException()).when(dao).save(student);
		Mockito.doThrow(new DaoException()).when(dao).save(teacher);
		Mockito.doThrow(new DaoException()).when(dao).save(eu);
		Mockito.doNothing().when(dao).save(newEU);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteEU);
		Mockito.doNothing().when(dao).save(newGroupStudent);
		Mockito.doThrow(new DaoException()).when(dao).save(groupStudentCM);
		Mockito.doThrow(new DaoException()).when(dao).save(groupStudentTD);
		Mockito.doThrow(new DaoException()).when(dao).save(groupStudentTP);
		Mockito.doThrow(new DaoException()).when(dao).save(
				incompleteGroupStudent);
		
		/* Remove */
		Mockito.doNothing().when(dao).remove(admin);
		Mockito.doNothing().when(dao).remove(student);
		Mockito.doNothing().when(dao).remove(teacher);
		Mockito.doThrow(new DaoException()).when(dao).remove(newAdmin);
		Mockito.doThrow(new DaoException()).when(dao).remove(newStudent);
		Mockito.doThrow(new DaoException()).when(dao).remove(newTeacher);
		Mockito.doNothing().when(dao).remove(eu);
		Mockito.doThrow(new DaoException()).when(dao).remove(newEU);
		Mockito.doNothing().when(dao).remove(groupStudentCM);
		Mockito.doNothing().when(dao).remove(groupStudentTD);
		Mockito.doNothing().when(dao).remove(groupStudentTP);
		Mockito.doThrow(new DaoException()).when(dao).remove(newGroupStudent);
		
		/* RemoveById */
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, admin.getEmail());
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, student.getEmail());
		Mockito.doNothing().when(dao).removeById(
				AbstractUser.class, teacher.getEmail());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newAdmin.getEmail());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newStudent.getEmail());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				AbstractUser.class, newTeacher.getEmail());
		Mockito.doNothing().when(dao).removeById(
				EU.class, eu.getId());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				EU.class, newEU.getId());
		Mockito.doNothing().when(dao).removeById(
				GroupStudent.class, groupStudentCM.getId());
		Mockito.doNothing().when(dao).removeById(
				GroupStudent.class, groupStudentTD.getId());
		Mockito.doNothing().when(dao).removeById(
				GroupStudent.class, groupStudentTP.getId());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				GroupStudent.class, newGroupStudent.getId());
		
		Mockito.doNothing().when(dao).flush();
		// Manager to test with mocked doa injection
		manager = new Manager(dao);
	}
	
	@After
	public void disconnect() {
		try {
			manager.logout();
		} catch (IllegalAccessException e) {}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void constructor() {
		Manager m;
		m = new Manager();
		assertTrue(m instanceof Manager);
		m = new Manager(dao);
		assertTrue(m instanceof Manager);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void LoginWithExistent()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.login(student.getEmail(), "student"));
		manager.logout();
		assertTrue(manager.login(teacher.getEmail(), "teacher"));
		manager.logout();
		assertTrue(manager.login(admin.getEmail(), "admin"));
		manager.logout();
	}
	
	@Test
	public void LoginWithWrongPassword() throws DaoException {
		assertFalse(manager.login(admin.getEmail(), "wrongPwd"));
	}
	
	@Test
	public void LoginWithNonExistent() throws DaoException {
		manager.login("absente@projet_edt.com", "password");
	}
	
	@Test(expected = IllegalAccessException.class)
	public void LogoutWithoutLoggedUser() throws IllegalAccessException {
		manager.logout();
	}
	
	@Test
	public void loginALoggedUser()
			throws DaoException, IllegalAccessException {
		Manager anotherManager = new Manager(dao);
		assertTrue(anotherManager.login(admin.getEmail(), "admin"));
		assertFalse(manager.login(admin.getEmail(), "admin"));
		anotherManager.logout();
	}
	
	@Test
	public void getCurrentUserWithUserConnected()
			throws DaoException, IllegalAccessException {
		assertNull(manager.getCurrentUser());
		manager.login(admin.getEmail(), "admin");
		assertEquals(admin, manager.getCurrentUser());
		manager.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void addNonExistentUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertTrue(manager.addUser(newStudent));
		assertTrue(manager.addUser(newTeacher));
		assertTrue(manager.addUser(newAdmin));
		manager.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithTeacherLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.login(teacher.getEmail(), "teacher"));
		try {
			manager.addUser(newStudent);
		} finally {
			manager.logout();
		}
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithStudentLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.login(student.getEmail(), "student"));
		try {
			manager.addUser(newStudent);
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void addIncompleteUserWithAdminLogged()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertFalse(manager.addUser(incompleteStudent));
		assertFalse(manager.addUser(incompleteTeacher));
		assertFalse(manager.addUser(incompleteAdmin));
		manager.logout();
	}
	
	@Test
	public void addExistentUserWithAdminLogged()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.login(admin.getEmail(), "admin"));
		assertFalse(manager.addUser(student));
		assertFalse(manager.addUser(teacher));
		assertFalse(manager.addUser(admin));
		manager.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithoutLoggedUser()
			throws IllegalAccessException {
		manager.addUser(newStudent);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void removeUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.removeUser(student.getEmail()));
		assertTrue(manager.removeUser(teacher.getEmail()));
		// Test don't remove admin cause tested in another test
		manager.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithTeacherLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(teacher.getEmail(), "teacher");
			manager.removeUser(student.getEmail());
		} finally {
			manager.logout();
		}
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.removeUser(teacher.getEmail());
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void removeUserLogged()
			throws DaoException, IllegalAccessException {
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
	public void removeUserWithoutUserLogged()
			throws IllegalAccessException {
		manager.removeUser(student.getEmail());
	}
	
	@Test
	public void removeNonExistentUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.removeUser(newAdmin.getEmail()));
		assertFalse(manager.removeUser(newStudent.getEmail()));
		assertFalse(manager.removeUser(newTeacher.getEmail()));
		manager.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentEUWithoutLoggedUser()
			throws IllegalAccessException {
		manager.addEU(newEU);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.addEU(newEU);
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void addNonExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.addEU(newEU));
		manager.logout();
	}
	
	@Test
	public void addExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.addEU(eu));
		manager.logout();
	}
	
	@Test
	public void addIncompleteEuWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.addEU(incompleteEU));
		manager.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentEUWithoutLoggedUser()
			throws IllegalAccessException {
		manager.removeEU(eu.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void remvoeExistentEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.removeEU(eu.getId());
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void removeExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.removeEU(eu.getId()));
		manager.logout();
	}
	
	@Test
	public void removeNonExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.removeEU(newEU.getId()));
		manager.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupStudentWithoutLoggedUser()
			throws IllegalAccessException {
		manager.addGroupStudent(newGroupStudent);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupStudentWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.addGroupStudent(newGroupStudent);
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void addNonExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.addGroupStudent(newGroupStudent));
		manager.logout();
	}
	
	@Test
	public void addExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.addGroupStudent(groupStudentCM));
		manager.logout();
	}
	
	@Test
	public void addIncompleteGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.addGroupStudent(incompleteGroupStudent));
		manager.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupStudentWithouLoggedUser()
			throws IllegalAccessException {
		manager.removeGroupStudent(groupStudentCM.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupStudentWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.login(student.getEmail(), "student");
			manager.removeGroupStudent(groupStudentCM.getId());
		} finally {
			manager.logout();
		}
	}
	
	@Test
	public void removeExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertTrue(manager.removeGroupStudent(groupStudentCM.getId()));
		manager.logout();
	}
	
	@Test
	public void removeNonExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.login(admin.getEmail(), "admin");
		assertFalse(manager.removeGroupStudent(newGroupStudent.getId()));
		manager.logout();
	}
}
