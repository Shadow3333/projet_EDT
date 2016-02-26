package business;

import java.util.HashMap;
import java.util.Map;

import business.dao.IDao;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
import business.model.GroupStudent;
import business.model.Session;
import business.model.users.AbstractUser;
import util.Hasher;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 *
 */
public class Manager {

	private IDao dao;
	private AbstractUser currentUser = null;
	private static Map<String, AbstractUser> userMap;

	public Manager() {}

	public Manager(IDao dao){
		this.dao = dao;
		userMap = new HashMap<String, AbstractUser>();
	}

	/**
	 * @param email
	 * @param hashPwd
	 */
	public synchronized boolean login(String email, String password) {
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
	public boolean addUser(AbstractUser user){
		return (user.getEmail() != null && user.getHashPwd() != null) ? dao.save(user) : false;
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean removeUser(String email){
		AbstractUser removeUser = dao.find(AbstractUser.class, email);
		return dao.remove(removeUser);
	}

	/**
	 * @param course
	 * @return
	 */
	public boolean addCourse(Courses course){
		return (course.getId() == null && course.getObligatories() == null) 
				? dao.save(course) : false;

	}

	/**
	 * @param course
	 * @return
	 */
	public boolean removeCourse(String course){
		Courses removeCourse = dao.find(Courses.class, course);
		return dao.remove(removeCourse);
	}

	/**
	 * @param eu
	 * @return
	 */
	public boolean addEU(EU eu){
		return (eu.getId() != null && eu.getName() != null) ? dao.save(eu) : false; 
	}

	/**
	 * @param eu
	 * @return
	 */
	public boolean removeEU(String eu){
		EU removeEu = dao.find(EU.class, eu);
		return dao.remove(removeEu);
	}
	
	/**
	 * @param session
	 * @return
	 */
	public boolean addSession(Session session){
		return (session.getId() != null &&
				session.getEu() != null &&
				session.getDate() != null &&
				session.getNbHour() != null) 
					? dao.save(session) : false;
	}

	/**
	 * @param session
	 * @return
	 */
	public boolean removeSession(String session){
		Session removeSession = dao.find(Session.class, session);
		return dao.remove(removeSession);
	}
	
	/**
	 * @param group
	 * @return
	 */
	public boolean addGroupEU(GroupEU group){
		return (group.getId() != null && group.getOptionnal() != null) ? dao.save(group) : false;
	}

	/**
	 * @param group
	 * @return
	 */
	public boolean removeGroupEU(String group){
		GroupEU removeGroup = dao.find(GroupEU.class, group);
		return dao.remove(removeGroup);
	}

	public boolean addGroupStudent(GroupStudent group){
		return (group.getId() != null) ? dao.save(group) : false;
	}

	public boolean removeGroupStudent(String group){
		GroupStudent removeGroup = dao.find(GroupStudent.class, group);
		return dao.remove(removeGroup);
	}

}
