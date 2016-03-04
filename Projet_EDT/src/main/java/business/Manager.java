package business;

import java.util.HashMap;
import java.util.Map;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
import business.model.GroupStudent;
import business.model.Session;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Teacher;
import util.Hasher;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 * @deprecated
 */
public class Manager {
	private IDao dao;
	
	private AbstractUser currentUser = null;
	private static Map<String, AbstractUser> userMap;
	
	private Manager() {
		userMap = new HashMap<String, AbstractUser>();
	}

	public Manager(IDao dao){
		this();
		this.dao = dao;
	}
	
	public AbstractUser getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * @param email
	 * @param hashPwd
	 * @throws DaoException 
	 */
	public synchronized boolean login(String email, String password) throws DaoException {
		String hashPwd = Hasher.SHA256(password);
		
		if(userMap.containsKey(email)) {
			return false;
		}

		AbstractUser u = dao.find(AbstractUser.class, email);
		if(u != null && u.getHashPwd().equals(hashPwd))
		{
			currentUser = u;
			userMap.put(email, u);
			return true;
		}
		return false;
	}

	/**
	 * @throws IllegalAccessException 
	 * 
	 */
	public synchronized void logout() throws IllegalAccessException {
		if(currentUser == null) {
			throw new IllegalAccessException("No user connected !");
		}
		userMap.remove(currentUser.getEmail());
		currentUser = null;
	}

	/**
	 * @param user
	 * @return
	 */
	public boolean addUser(AbstractUser user) throws IllegalAccessException {
		if(currentUser == null || !(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to add User");
		}
		try {
			dao.save(user);
			dao.flush();
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param email
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeUser(String email) throws IllegalAccessException {
		if(currentUser == null ||
				!(currentUser instanceof Admin)
				&& !currentUser.getEmail().equals(email)) {
			throw new IllegalAccessException(
					"Current user haven't right to remove this user");
		}
		try {
			dao.removeById(AbstractUser.class, email);
			dao.flush();
			if(currentUser.getEmail().equals(email)) {
				currentUser= null;
				userMap.remove(email);
			}
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param course
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean addCourses(Courses course) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to add Courses");
		}
		try {
			dao.save(course);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param course
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeCourses(String course) throws IllegalAccessException{
		if(currentUser == null || !(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove Courses");
		}
		try {
			dao.removeById(Courses.class, course);
			dao.flush();
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param eu
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean addEU(EU eu) throws IllegalAccessException{
		/*
		 * TODO Can a teacher add an EU ?
		 * Yes, so add in condition
		 * No, remove these comments
		 */
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to add EU");
		}
		try {
			dao.save(eu);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param eu
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeEU(String eu) throws IllegalAccessException{
		/*
		 * TODO Can a teacher remove an EU ?
		 * Yes, so add in condition
		 * No, remove these comments
		 */
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove EU");
		}
		try {
			dao.removeById(EU.class, eu);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param session
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean addSession(Session session) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin) &&
				(session.getTeacher() == null ||
				session.getTeacher() != currentUser)) {
			throw new IllegalAccessException(
					"Current user haven't rights to add this Session");
		}
		try {
			dao.save(session);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param session
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeSession(String session) throws IllegalAccessException {
		Session s = null;
		try {
			s = dao.find(Session.class, session);
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		if(currentUser == null ||
				!(currentUser instanceof Admin) &&
				(s.getTeacher() == null ||
				!(currentUser instanceof Teacher) ||
				s.getTeacher() != currentUser)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove this Session");
		}
		try {
			dao.removeById(Session.class, session);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean addGroupEU(GroupEU group) throws IllegalAccessException {
		// TODO Maybe different than Admin can add GroupEU
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove EU");
		}
		try {
			dao.save(group);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeGroupEU(Long group) throws IllegalAccessException {
		// TODO Maybe different than Admin can remove GroupEU
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove EU");
		}
		try {
			dao.removeById(GroupEU.class, group);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException  
	 */
	public boolean addGroupStudent(GroupStudent group) throws IllegalAccessException {
		/*
		 * TODO Can another User add a GroupStudent ?
		 * Yes, so add in condition
		 * No, remove these comments
		 */
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to add GroupStudent");
		}
		try {
			dao.save(group);
			dao.flush();
		} catch (DaoException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean removeGroupStudent(Long group) throws IllegalAccessException{
		/*
		 * TODO Can another User remove a GroupStudent ?
		 * Yes, so add in condition
		 * No, remove these comments
		 */
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to remove GroupStudent");
		}
		try {
			dao.removeById(GroupStudent.class, group);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param course
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean updateCourse(Courses course) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update Courses");
		}
		try {
			course = (Courses) dao.update(course);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param eu
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean updateEU(EU eu) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update EU");
		}
		try {
			eu = (EU) dao.update(eu);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean updateGroupEU(GroupEU group) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update GroupEU");
		}
		try {
			group = (GroupEU) dao.update(group);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param group
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean updateGroupStudent(GroupStudent group) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update GroupStudent");
		}
		try {
			group = (GroupStudent) dao.update(group);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param session
	 * @return
	 * @throws IllegalAccessException 
	 */
	public boolean updateSession(Session session) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update Session");
		}
		try {
			session = (Session) dao.update(session);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param user
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean updateUser(AbstractUser user) throws IllegalAccessException{
		if(currentUser == null ||
				!(currentUser instanceof Admin)) {
			throw new IllegalAccessException(
					"Current user haven't rights to update AbstractUser");
		}
		try {
			user = (AbstractUser) dao.update(user);
			dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;	
	}
}
