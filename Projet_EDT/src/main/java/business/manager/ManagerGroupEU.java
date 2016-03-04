package business.manager;

import business.dao.IDao;
import business.model.GroupEU;
import business.model.users.Admin;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 */
public class ManagerGroupEU extends AbstractManager<GroupEU> {

	public ManagerGroupEU(IDao dao, Manager manager) {
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
