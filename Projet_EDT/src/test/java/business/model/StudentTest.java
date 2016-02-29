package business.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import business.model.users.AbstractUser;
import business.model.users.AbstractUser.PhoneType;
import business.model.users.Student;
import util.Hasher;

/**
 * @author DUBUIS Michael
 *
 */
public class StudentTest {
	private Student student = new Student();
	
	private String email = "student@projet_EDT.com";
	private String lastName = "lastName";
	private String firstName = "firstName";
	private Date birthDate = new Date(System.currentTimeMillis());
	private String password = "student";
	private String hashPassword = Hasher.SHA256(password);
	private String webSite = "http://projet_EDT.com/";
	private Map<PhoneType, String> phones =
			new HashMap<AbstractUser.PhoneType, String>();
	private List<GroupEU> groups = new ArrayList<GroupEU>();
	private String course = "M2ISL"; 
	
	@Test
	public void constructor() {
		Student s = new Student();
		assertTrue(s instanceof Student);
		assertTrue(s instanceof AbstractUser);
	}
	
	@Test
	public void email() {
		student.setEmail(email);
		assertEquals(email, student.getEmail());
	}
	
	@Test
	public void lastName() {
		student.setLastName(lastName);
		assertEquals(lastName, student.getLastName());
	}
	
	@Test
	public void firstName() {
		student.setFirstName(firstName);
		assertEquals(firstName, student.getFirstName());
	}
	
	@Test
	public void hashPwd() {
		student.setHashPwd(hashPassword);
		assertEquals(hashPassword, student.getHashPwd());
		student.setPassword(password);
		assertEquals(hashPassword, student.getHashPwd());
	}
	
	@Test
	public void birthDate() {
		student.setBirthDate(birthDate);
		assertEquals(birthDate, student.getBirthDate());
	}
	
	@Test
	public void phones() {
		assertNotNull(PhoneType.values());
		assertEquals(PhoneType.valueOf("home"),PhoneType.home);
		phones.put(PhoneType.home, "+33400000000");
		phones.put(PhoneType.mobile, "+33600000000");
		phones.put(PhoneType.work, "NC");
		student.setPhones(phones);
		assertEquals(phones, student.getPhones());
	}
	
	@Test
	public void webSite() {
		student.setWebSite(webSite);
		assertEquals(webSite, student.getWebSite());
	}
	
	@Test
	public void groups() {
		student.setGroups(groups);
		assertEquals(groups, student.getGroups());
	}
	
	@Test
	public void course() {
		student.setIdCourses(course);
		assertEquals(course, student.getIdCourses());
	}
}
