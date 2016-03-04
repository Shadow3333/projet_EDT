package business.manager;

import business.dao.IDao;
import business.model.EU;
import business.model.users.Admin;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 */
public class ManagerEUs extends AbstractManager<EU>{
	/**
	 * @param dao
	 */
	public ManagerEUs(IDao dao, Manager manager) {
		super(dao, manager);
	}

	@Override
	public boolean canSave() {
		if(manager.managerUsers.getCurrentUser() != null
				&& manager.managerUsers.getCurrentUser() instanceof Admin) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canRemove() {
		if(manager.managerUsers.getCurrentUser() != null
				&& manager.managerUsers.getCurrentUser() instanceof Admin) {
			return true;
		}
		return false;
	}
}
