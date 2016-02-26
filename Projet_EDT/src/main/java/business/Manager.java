package business;

import java.util.List;

import business.dao.IDao;
import business.model.users.AbstractUser;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michaël
 *
 */
public class Manager {

	private IDao dao;
	private static Manager currentInstance = null;
	private List<AbstractUser> userList;
	
	private Manager(){}
	
	/**
	 * @return
	 */
	public static Manager newInstance() {
		currentInstance = new Manager();
		return currentInstance;
	}

	/**
	 * @param dao
	 * @return
	 */
	public static Manager newInstance(IDao dao) {
		currentInstance = new Manager();
		currentInstance.dao = dao;
		return currentInstance;
	}

	/**
	 * @param email
	 * @param hashPwd
	 */
	public synchronized boolean login(String email, String hashPwd) {
		AbstractUser user = currentInstance.dao.find(AbstractUser.class, email);
		if(userList.contains(user))
			return false;
		if(user.getHashPwd().equals(hashPwd))
		{
			userList.add(user);
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public synchronized void logout() {
		
		
	}

	/**
	 * @return
	 */
	public static Manager getInstance() {
		if(currentInstance != null)
			return currentInstance;
		return newInstance();
	}

}
