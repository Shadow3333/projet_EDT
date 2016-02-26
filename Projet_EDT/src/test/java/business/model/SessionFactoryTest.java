package business.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import business.dao.IDao;
import business.model.EU.LessonType;
import business.model.users.AbstractUser;
import business.model.users.Student;
import business.model.users.Teacher;

/**
 * @author DUBUIS Michael
 *
 */
public class SessionFactoryTest {
	/*
	 * IDao is mocked for not use DataBase
	 */
	private static IDao dao;

	/*
	 * Class to test
	 */
	private static SessionFactory factory;

	/*
	 * Variables for tests
	 */
	private static Courses courses = new Courses();
	private static EU eu = new EU();
	private static GroupEU groupEU = new GroupEU();
	private static AbstractUser student = new Student();
	private static AbstractUser teacher = new Teacher();
	private static GroupStudent groupStudentCM = new GroupStudent();
	private static GroupStudent groupStudentTD = new GroupStudent();
	private static GroupStudent groupStudentTP = new GroupStudent();
	private static Date date = new Date(System.currentTimeMillis());
	
	@BeforeClass
	public static void init() {
		//
		dao = Mockito.mock(IDao.class);
		// Variables initialization
		eu.setId("AMU0001");
		eu.setName("Test");
		student.setEmail("student@projet_edt.com");
		student.setPassword("student");
		teacher.setEmail("teacher@projet_edt.com");
		teacher.setPassword("teacher");
		List<EU> listEU = new ArrayList<EU>();
		listEU.add(eu);
		((Teacher) teacher).setCm(listEU);
		((Teacher) teacher).setTd(listEU);
		((Teacher) teacher).setTp(listEU);
		List<AbstractUser> listStudents = new ArrayList<AbstractUser>();
		listStudents.add(student);
		groupStudentCM.setStudents(listStudents);
		groupStudentCM.setGroupType(LessonType.CM);
		groupStudentTD.setStudents(listStudents);
		groupStudentTD.setGroupType(LessonType.TD);
		groupStudentTP.setStudents(listStudents);
		groupStudentTP.setGroupType(LessonType.TP);
		Map<Integer, GroupStudent> mapTD =
				new HashMap<Integer, GroupStudent>();
		mapTD.put(1, groupStudentTD);
		Map<Integer, GroupStudent> mapTP =
				new HashMap<Integer, GroupStudent>();
		mapTP.put(1, groupStudentTP);
		groupEU.setEus(listEU);
		groupEU.setCm(groupStudentCM);
		groupEU.setTd(mapTD);
		groupEU.setTp(mapTP);
		groupEU.setOptionnal(false);
		courses.setObligatories(groupEU);
		// Mock comportement
		Mockito.when(dao.find(EU.class, eu.getId())).thenReturn(eu);
		Mockito.when(dao.find(EU.class, "wrongId")).thenReturn((EU) null);
		Mockito.when(dao.find(Teacher.class, teacher.getEmail())).thenReturn((Teacher) teacher);
		Mockito.when(dao.find(AbstractUser.class, teacher.getEmail())).thenReturn(teacher);

		factory = new SessionFactory(dao);
	}
	
	@Test
	public void settersAndGetters() {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(2);
		factory.setNumGroup(1);
		factory.setType(LessonType.TD);
		factory.setRoom("A001");
		factory.setTeacher(teacher.getEmail());
		assertTrue(factory.getCourses().equals(courses));
		assertTrue(factory.getDate().equals(date));
		assertTrue(factory.getIdEU().equals(eu.getId()));
		assertTrue(factory.getNbHour() == 2);
		assertTrue(factory.getType().equals(LessonType.TD));
		assertTrue(factory.getNumGroup() == 1);
		assertTrue(factory.getRoom().equals("A001"));
		assertTrue(factory.getTeacher().equals(teacher.getEmail()));
	}
	
	@Test
	public void notStatic() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(2);
		factory.setType(LessonType.CM);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
		
		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void notStaticUnexistentEU() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setNbHour(2);
		factory.setType(LessonType.CM);
		factory.setIdEU("WrongId");
		factory.setRoom("A001");
		
		Session session = factory.createSession();
	}
	
	@Test
	public void notStaticTD() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(2);
		factory.setNumGroup(1);
		factory.setType(LessonType.TD);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
		
		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = MalformedParametersException.class)
	public void notStaticTDWithoutNumGroup() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(2);
		factory.setType(LessonType.TD);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
	}
	
	@Test
	public void notStaticTP() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(2);
		factory.setNumGroup(1);
		factory.setType(LessonType.TP);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
		
		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = MalformedParametersException.class)
	public void notStaticEmpty() throws Exception {
		Session session = factory.createSession();
	}
	
	@SuppressWarnings("unused")
	@Test(expected = MalformedParametersException.class)
	public void notStaticWrongHour() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(-1);
		factory.setNumGroup(1);
		factory.setType(LessonType.CM);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
	}
	
	@SuppressWarnings("unused")
	@Test(expected = MalformedParametersException.class)
	public void NotStaticTDWithoutNumGroup() throws Exception {
		factory.setCourses(courses);
		factory.setDate(date);
		factory.setIdEU(eu.getId());
		factory.setNbHour(-1);
		factory.setNumGroup(null);
		factory.setType(LessonType.CM);
		factory.setRoom("A001");
		
		Session session = factory.createSession();
	}
	
	@Test
	public void staticWithoutTeacher() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001");
		
		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@Test
	public void staticTeacherNull() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001",
				null);

		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@Test
	public void staticTeacherCM() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001",
				teacher.getEmail());

		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void staticTeacherNotCM() throws Exception {
		try {
			((Teacher)teacher).setCm(new ArrayList<EU>());
			Session session = SessionFactory.createSession(
					dao,
					courses,
					eu.getId(),
					date,
					1,
					"A001",
					teacher.getEmail());
		} finally {
			List<EU> listEU = new ArrayList<EU>();
			listEU.add(eu);
			((Teacher) teacher).setCm(listEU);
		}
	}

	@Test
	public void staticTeacherTD() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001",
				LessonType.TD,
				1,
				teacher.getEmail());

		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void staticTeacherNotTD() throws Exception {
		try {
			((Teacher)teacher).setTd(new ArrayList<EU>());
			Session session = SessionFactory.createSession(
					dao,
					courses,
					eu.getId(),
					date,
					1,
					"A001",
					LessonType.TD,
					1,
					teacher.getEmail());
		} finally {
			List<EU> listEU = new ArrayList<EU>();
			listEU.add(eu);
			((Teacher) teacher).setTd(listEU);
		}
	}

	@Test
	public void staticTeacherTP() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001",
				LessonType.TP,
				1,
				teacher.getEmail());

		assertTrue(session instanceof Session);
		assertNotNull(session);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void staticTeacherNotTP() throws Exception {
		try {
			((Teacher)teacher).setTp(new ArrayList<EU>());
			Session session = SessionFactory.createSession(
					dao,
					courses,
					eu.getId(),
					date,
					1,
					"A001",
					LessonType.TP,
					1,
					teacher.getEmail());
		} finally {
			List<EU> listEU = new ArrayList<EU>();
			listEU.add(eu);
			((Teacher) teacher).setTp(listEU);
		}
	}
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void staticStudentInTeacher() throws Exception {
		Session session = SessionFactory.createSession(
				dao,
				courses,
				eu.getId(),
				date,
				1,
				"A001",
				student.getEmail());
	}
}
