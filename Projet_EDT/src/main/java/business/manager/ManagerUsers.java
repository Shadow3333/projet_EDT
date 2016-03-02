package business.manager;

import business.dao.IDao;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
 * @author DUBUIS Michael
 *
 */
public class ManagerUsers extends AbstractManager<AbstractUser> {

	/**
	 * @param dao
	 */
	public ManagerUsers(IDao dao) {
		super(dao);
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
}
