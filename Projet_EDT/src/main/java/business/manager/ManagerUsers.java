package business.manager;

import java.util.HashMap;
import java.util.Map;

import business.dao.DaoException;
import business.dao.IDao;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import util.Hasher;

/**
 * @author DUBUIS Michael
 *
 */
public class ManagerUsers extends AbstractManager<AbstractUser> {

	private AbstractUser currentUser = null;
	private static Map<String, AbstractUser> userMap;
	
	/**
	 * @param dao
	 */
	public ManagerUsers(IDao dao) {
		super(dao);
		userMap = new HashMap<String, AbstractUser>();
	}

	@Override
	public boolean canSave(AbstractUser user) {
		if(user instanceof Admin) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canRemove(AbstractUser user) {
		if(user instanceof Admin) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canFindAll(AbstractUser user) {
		return true;
	}

	@Override
	public boolean canFind(AbstractUser user) {
		return true;
	}
	
	@Override
	public boolean canUpdate(AbstractUser user) {
		return true;
	}
	
	/**
	 * Log a user with his email and password
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
