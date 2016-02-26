package business;

import java.util.Map;

import business.dao.IDao;
import business.model.GroupEU;
import business.model.Session;
import business.model.users.AbstractUser;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michaël
 *
 */
public class Manager {

	private IDao dao;
	private AbstractUser currentUser;
	private static Map<String, AbstractUser> userMap;

	public Manager() {}

	public Manager(IDao dao){
		this.dao = dao;
	}

	/**
	 * @param email
	 * @param hashPwd
	 */
	public synchronized boolean login(String email, String hashPwd) {
		if(userMap.containsKey(email))
			return false;

		currentUser = dao.find(AbstractUser.class, email);
		if(currentUser.getHashPwd().equals(hashPwd) && currentUser != null)
		{
			userMap.put(email, currentUser);
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public synchronized void logout() {
		userMap.remove(currentUser.getEmail());
	}

	/**
	 * @param user
	 * @return
	 */
	public boolean addUser(AbstractUser user){
		return (user.getEmail() == null || user.getHashPwd() == null) ? false : dao.save(user);
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
	 * @param group
	 * @return
	 */
	public boolean addGroupEU(GroupEU group){
		return (group.getId() == null || 
				group.getEus() == null || 
				group.getTd() == null ||
				group.getTp() == null ||
				group.getCm() != null) ? false : dao.save(group);
	}

	/**
	 * @param group
	 * @return
	 */
	public boolean removeGroupEU(String group){
		GroupEU removeGroup = dao.find(GroupEU.class, group);
		return dao.remove(removeGroup);
	}

	/**
	 * @param session
	 * @return
	 */
	public boolean addSession(Session session){
		return (session.getId() == null) ? false : dao.save(session);
	}

	/**
	 * @param session
	 * @return
	 */
	public boolean removeSession(String session){
		Session removeSession = dao.find(Session.class, session);
		return dao.remove(removeSession);
		
	}

}
