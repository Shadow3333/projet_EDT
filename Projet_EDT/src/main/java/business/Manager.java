package business;

import java.util.Map;

import javax.management.RuntimeErrorException;

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
	private Map<String, AbstractUser> userMap;
	
	private Manager(){}
	
	/**
	 * @return
	 */
	public static Manager newInstance() {
		if(currentInstance != null) 
			throw new RuntimeErrorException(null, "This class is already instantiated");
		
		currentInstance = new Manager();
		return currentInstance;
	}

	/**
	 * @param dao
	 * @return
	 */
	public static Manager newInstance(IDao dao) {
		if(currentInstance != null) 
			throw new RuntimeErrorException(null, "This class is already instantiated");		
		
		currentInstance = new Manager();
		currentInstance.dao = dao;
		return currentInstance;
	}

	/**
	 * @param email
	 * @param hashPwd
	 */
	public synchronized boolean login(String email, String hashPwd) {
		if(userMap.containsKey(email))
			return false;
		
		AbstractUser user = currentInstance.dao.find(AbstractUser.class, email);
		if(user.getHashPwd().equals(hashPwd) && user != null)
		{
			userMap.put(email, user);
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
