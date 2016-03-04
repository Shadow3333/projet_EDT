package business.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
import business.model.GroupStudent;
import business.model.Session;
import business.model.EU.LessonType;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Student;
import business.model.users.Teacher;

/**
 * @author DUBUIS Michael
 *
 */
public class DaoMocked {
	/*
	 * IDao is mocked for not use DataBase
	 */
	public IDao dao;
	
	/*
	 * Variables for tests
	 */
	public AbstractUser student = new Student();
	public AbstractUser teacher = new Teacher();
	public AbstractUser admin = new Admin();
	public AbstractUser newStudent = new Student();
	public AbstractUser newTeacher = new Teacher();
	public AbstractUser newAdmin = new Admin();
	public AbstractUser incompleteStudent = new Student();
	public AbstractUser incompleteTeacher = new Teacher();
	public AbstractUser incompleteAdmin = new Admin();
	public EU eu = new EU();
	public EU newEU = new EU();
	public EU incompleteEU = new EU();
	public GroupStudent groupStudentCM = new GroupStudent();
	public GroupStudent groupStudentTD = new GroupStudent();
	public GroupStudent groupStudentTP = new GroupStudent();
	public GroupStudent newGroupStudent = new GroupStudent();
	public GroupStudent incompleteGroupStudent = new GroupStudent();
	public GroupEU groupEUObligatory = new GroupEU();
	public GroupEU groupEUOptionnal = new GroupEU();
	public GroupEU newGroupEU = new GroupEU();
	public GroupEU incompleteGroupEU = new GroupEU();
	public Courses courses = new Courses();
	public Courses newCourses = new Courses();
	public Courses incompleteCourses = new Courses();
	
	/**
	 * @throws DaoException 
	 * 
	 */
	public DaoMocked() throws DaoException {
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
		
		groupEUObligatory.setId(1L);
		List<EU> listEUForGroupEU = new ArrayList<EU>();
		listEUForGroupEU.add(eu);
		groupEUObligatory.setEus(listEUForGroupEU);
		groupEUObligatory.setOptionnal(false);
		groupEUObligatory.setCm(groupStudentCM);
		Map<Integer, GroupStudent> mapTD =
				new HashMap<Integer, GroupStudent>();
		mapTD.put(1, groupStudentTD);
		groupEUObligatory.setTd(mapTD);
		Map<Integer, GroupStudent> mapTP =
				new HashMap<Integer, GroupStudent>();
		mapTP.put(1, groupStudentTP);
		groupEUObligatory.setTp(mapTP);
		groupEUOptionnal.setId(2L);
		groupEUOptionnal.setEus(listEUForGroupEU);
		groupEUOptionnal.setOptionnal(true);
		groupEUOptionnal.setCm(groupStudentCM);
		groupEUOptionnal.setTd(mapTD);
		groupEUOptionnal.setTp(mapTP);
		newGroupEU.setId(3L);
		
		courses.setId("Courses");
		courses.setName("Existent courses");
		courses.setObligatories(groupEUObligatory);
		List<GroupEU> listOptionsForCourses = new ArrayList<GroupEU>();
		courses.setOptions(listOptionsForCourses);
		newCourses.setId("newCourses");
		newCourses.setName("Non-existent courses");
		
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
		Mockito.when(
				dao.find(GroupEU.class, groupEUObligatory.getId())
				).thenReturn(groupEUObligatory);
		Mockito.when(
				dao.find(GroupEU.class, groupEUOptionnal.getId())
				).thenReturn(groupEUOptionnal);
		Mockito.when(
				dao.find(GroupEU.class, newGroupEU.getId())
				).thenThrow(new DaoException());
		Mockito.when(
				dao.find(Courses.class, courses.getId())
				).thenReturn(courses);
		Mockito.when(
				dao.find(Courses.class, newCourses.getId())
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
		List<GroupEU> listGroupEU = new ArrayList<GroupEU>();
		listGroupEU.add(groupEUObligatory);
		listGroupEU.add(groupEUOptionnal);
		Mockito.when(dao.findAll(GroupEU.class)).thenReturn(listGroupEU);
		List<Courses> listCourses = new ArrayList<Courses>();
		listCourses.add(courses);
		Mockito.when(dao.findAll(Courses.class)).thenReturn(listCourses);
		
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
		Mockito.doNothing().when(dao).save(newGroupEU);
		Mockito.doThrow(new DaoException()).when(dao).save(groupEUObligatory);
		Mockito.doThrow(new DaoException()).when(dao).save(groupEUOptionnal);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteGroupEU);
		Mockito.doNothing().when(dao).save(newCourses);
		Mockito.doThrow(new DaoException()).when(dao).save(courses);
		Mockito.doThrow(new DaoException()).when(dao).save(incompleteCourses);
		
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
		Mockito.doNothing().when(dao).remove(groupEUObligatory);
		Mockito.doNothing().when(dao).remove(groupEUOptionnal);
		Mockito.doThrow(new DaoException()).when(dao).remove(newGroupEU);
		Mockito.doNothing().when(dao).remove(courses);
		Mockito.doThrow(new DaoException()).when(dao).remove(newCourses);
		
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
		Mockito.doNothing().when(dao).removeById(
				GroupEU.class, groupEUObligatory.getId());
		Mockito.doNothing().when(dao).removeById(
				GroupEU.class, groupEUOptionnal.getId());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				GroupEU.class, newGroupEU.getId());
		Mockito.doNothing().when(dao).removeById(
				Courses.class, courses.getId());
		Mockito.doThrow(new DaoException()).when(dao).removeById(
				Courses.class, newCourses.getId());
		
		
		Mockito.when(dao.isEntity(AbstractUser.class)).thenReturn(true);
		Mockito.when(dao.isEntity(Courses.class)).thenReturn(true);
		Mockito.when(dao.isEntity(EU.class)).thenReturn(true);
		Mockito.when(dao.isEntity(GroupEU.class)).thenReturn(true);
		Mockito.when(dao.isEntity(GroupStudent.class)).thenReturn(true);
		Mockito.when(dao.isEntity(Session.class)).thenReturn(true);
		Mockito.doNothing().when(dao).flush();
	}

}
