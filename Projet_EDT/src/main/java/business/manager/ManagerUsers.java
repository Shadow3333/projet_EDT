package business.manager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import util.Hasher;

/**
 * @author DUBUIS Michael
 * @contributor LELIEVRE Romain
 */
public class ManagerUsers extends AbstractManager<AbstractUser> {

	private AbstractUser currentUser = null;
	private static Map<String, AbstractUser> userMap;
	
	/**
	 * @param dao
	 */
	public ManagerUsers(IDao dao, Manager manager) {
		super(dao, manager);
		userMap = new HashMap<String, AbstractUser>();
	}
	
	/**
	 * Return the current user
	 * @return
	 */
	public AbstractUser getCurrentUser() {
		return currentUser;
	}

	@Override
	public boolean canRemove() {
		if(manager.managerUsers.getCurrentUser() != null
				&& manager.managerUsers.getCurrentUser() instanceof Admin) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean remove(Serializable id)
			throws IllegalAccessException {
		if(!canRemove() &&
				(currentUser == null ||
				!currentUser.getEmail().equals(id))) {
			throw new IllegalAccessException();
		}
		try {
			dao.removeById(type, id);
			dao.flush();
			if(currentUser.getEmail().equals(id)) {
				currentUser = null;
				userMap.remove(id);
			}
			return true;
		} catch(DaoException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Log a user with his email and password
	 * @param email
	 * @param hashPwd
	 * @throws DaoException 
	 */
	public synchronized boolean login(String email, String password)
			throws DaoException {
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
	 * End of connection of a user
	 * @throws IllegalAccessException 
	 */
	public synchronized void logout() throws IllegalAccessException {
		if(currentUser == null) {
			throw new IllegalAccessException("No user connected !");
		}
		userMap.remove(currentUser.getEmail());
		currentUser = null;
	}
}
