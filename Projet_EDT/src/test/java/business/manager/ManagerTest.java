package business.manager;

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
import business.model.Courses;
import business.model.EU;
import business.model.EU.LessonType;
import business.model.GroupEU;
import business.model.GroupStudent;
import business.model.Session;
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
	private static DaoMocked mocker;
	
	/*
	 * Class to test
	 */
	private static Manager manager;
	
	
	@BeforeClass
	public static void init() throws DaoException {
		mocker = new DaoMocked();
		
		manager = new Manager(mocker.dao);
	}
	
	@After
	public void disconnect() {
		try {
			manager.managerUsers.logout();
		} catch (IllegalAccessException e) {}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void LoginWithExistent()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.managerUsers.login(mocker.student.getEmail(), "student"));
		manager.managerUsers.logout();
		assertTrue(manager.managerUsers.login(mocker.teacher.getEmail(), "teacher"));
		manager.managerUsers.logout();
		assertTrue(manager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		manager.managerUsers.logout();
	}
	
	@Test
	public void LoginWithWrongPassword() throws DaoException {
		assertFalse(manager.managerUsers.login(mocker.admin.getEmail(), "wrongPwd"));
	}
	
	@Test
	public void LoginWithNonExistent() throws DaoException {
		manager.managerUsers.login("absente@projet_edt.com", "password");
	}
	
	@Test(expected = IllegalAccessException.class)
	public void LogoutWithoutLoggedUser() throws IllegalAccessException {
		manager.managerUsers.logout();
	}
	
	@Test
	public void loginALoggedUser()
			throws DaoException, IllegalAccessException {
		Manager anotherManager = new Manager(mocker.dao);
		assertTrue(anotherManager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		assertFalse(manager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		anotherManager.managerUsers.logout();
	}
	
	@Test
	public void getCurrentUserWithUserConnected()
			throws DaoException, IllegalAccessException {
		assertNull(manager.managerUsers.getCurrentUser());
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertEquals(mocker.admin, manager.managerUsers.getCurrentUser());
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void addNonExistentUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		assertTrue(manager.managerUsers.save(mocker.newStudent));
		assertTrue(manager.managerUsers.save(mocker.newTeacher));
		assertTrue(manager.managerUsers.save(mocker.newAdmin));
		manager.managerUsers.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithTeacherLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.managerUsers.login(mocker.teacher.getEmail(), "teacher"));
		try {
			manager.managerUsers.save(mocker.newStudent);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithStudentLogged()
			throws DaoException, IllegalAccessException {
		assertTrue(manager.managerUsers.login(mocker.student.getEmail(), "student"));
		try {
			manager.managerUsers.save(mocker.newStudent);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void addIncompleteUserWithAdminLogged()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		assertFalse(manager.managerUsers.save(mocker.incompleteStudent));
		assertFalse(manager.managerUsers.save(mocker.incompleteTeacher));
		assertFalse(manager.managerUsers.save(mocker.incompleteAdmin));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addExistentUserWithAdminLogged()
			throws IllegalAccessException, DaoException {
		assertTrue(manager.managerUsers.login(mocker.admin.getEmail(), "admin"));
		assertFalse(manager.managerUsers.save(mocker.student));
		assertFalse(manager.managerUsers.save(mocker.teacher));
		assertFalse(manager.managerUsers.save(mocker.admin));
		manager.managerUsers.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentUserWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerUsers.save(mocker.newStudent);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test
	public void removeUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerUsers.remove(mocker.student.getEmail()));
		assertTrue(manager.managerUsers.remove(mocker.teacher.getEmail()));
		// Test don't remove admin cause tested in another test
		manager.managerUsers.logout();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithTeacherLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.teacher.getEmail(), "teacher");
			manager.managerUsers.remove(mocker.student.getEmail());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerUsers.remove(mocker.teacher.getEmail());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void removeUserLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		manager.managerUsers.remove(mocker.admin.getEmail());
		assertNull(manager.managerUsers.getCurrentUser());
		manager.managerUsers.login(mocker.student.getEmail(), "student");
		manager.managerUsers.remove(mocker.student.getEmail());
		assertNull(manager.managerUsers.getCurrentUser());
		manager.managerUsers.login(mocker.teacher.getEmail(), "teacher");
		manager.managerUsers.remove(mocker.teacher.getEmail());
		assertNull(manager.managerUsers.getCurrentUser());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeUserWithoutUserLogged()
			throws IllegalAccessException {
		manager.managerUsers.remove(mocker.student.getEmail());
	}
	
	@Test
	public void removeNonExistentUserWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerUsers.remove(mocker.newAdmin.getEmail()));
		assertFalse(manager.managerUsers.remove(mocker.newStudent.getEmail()));
		assertFalse(manager.managerUsers.remove(mocker.newTeacher.getEmail()));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentEUWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerEus.save(mocker.newEU);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerEus.save(mocker.newEU);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void addNonExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerEus.save(mocker.newEU));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerEus.save(mocker.eu));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addIncompleteEuWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerEus.save(mocker.incompleteEU));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentEUWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerEus.remove(mocker.eu.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void remvoeExistentEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerEus.remove(mocker.eu.getId());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void removeExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerEus.remove(mocker.eu.getId()));
		manager.managerUsers.logout();
	}
	
	@Test
	public void removeNonExistentEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerEus.remove(mocker.newEU.getId()));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupStudentWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerGroupStudent.save(mocker.newGroupStudent);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupStudentWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerGroupStudent.save(mocker.newGroupStudent);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void addNonExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerGroupStudent.save(mocker.newGroupStudent));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerGroupStudent.save(mocker.groupStudentCM));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addIncompleteGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerGroupStudent.save(mocker.incompleteGroupStudent));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupStudentWithouLoggedUser()
			throws IllegalAccessException {
		manager.managerGroupStudent.remove(mocker.groupStudentCM.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupStudentWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerGroupStudent.remove(mocker.groupStudentCM.getId());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void removeExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerGroupStudent.remove(mocker.groupStudentCM.getId()));
		manager.managerUsers.logout();
	}
	
	@Test
	public void removeNonExistentGroupStudentWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerGroupStudent.remove(mocker.newGroupStudent.getId()));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupEUWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managergroupEU.save(mocker.newGroupEU);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentGroupEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managergroupEU.save(mocker.newGroupEU);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void addNonExistentGroupEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managergroupEU.save(mocker.newGroupEU));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addExistentGroupEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managergroupEU.save(mocker.groupEUObligatory));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addIncompleteGroupEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managergroupEU.save(mocker.incompleteGroupEU));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupEUWithouLoggedUser()
			throws IllegalAccessException {
		manager.managergroupEU.remove(mocker.groupEUObligatory.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentGroupEUWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managergroupEU.remove(mocker.groupEUObligatory.getId());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void removeExistentGroupEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managergroupEU.remove(mocker.groupEUObligatory.getId()));
		manager.managerUsers.logout();
	}
	
	@Test
	public void removeNonExistentGroupEUWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managergroupEU.remove(mocker.newGroupEU.getId()));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentCoursesWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerCourses.save(mocker.newCourses);
	}
	
	@Test(expected = IllegalAccessException.class)
	public void addNonExistentCoursesWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerCourses.save(mocker.newCourses);
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void addNonExistentCoursesWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerCourses.save(mocker.newCourses));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addExistentCoursesWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerCourses.save(mocker.courses));
		manager.managerUsers.logout();
	}
	
	@Test
	public void addIncompleteCoursesWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerCourses.save(mocker.incompleteCourses));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentCoursesWithoutLoggedUser()
			throws IllegalAccessException {
		manager.managerCourses.remove(mocker.courses.getId());
	}
	
	@Test(expected = IllegalAccessException.class)
	public void removeExistentCoursesWithStudentLogged()
			throws DaoException, IllegalAccessException {
		try {
			manager.managerUsers.login(mocker.student.getEmail(), "student");
			manager.managerCourses.remove(mocker.courses.getId());
		} finally {
			manager.managerUsers.logout();
		}
	}
	
	@Test
	public void removeExistentCoursesWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertTrue(manager.managerCourses.remove(mocker.courses.getId()));
		manager.managerUsers.logout();
	}
	
	@Test
	public void removeNonExistentCoursesWithAdminLogged()
			throws DaoException, IllegalAccessException {
		manager.managerUsers.login(mocker.admin.getEmail(), "admin");
		assertFalse(manager.managerCourses.remove(mocker.newCourses.getId()));
		manager.managerUsers.logout();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
}
