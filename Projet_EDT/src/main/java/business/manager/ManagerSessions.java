package business.manager;

import business.dao.IDao;
import business.model.Session;
import business.model.SessionFactory;
import business.model.users.Admin;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 */
public class ManagerSessions extends AbstractManager<Session> {

	public ManagerSessions(IDao dao, Manager manager) {
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
	
	public SessionFactory createFactory() {
		return new SessionFactory(dao);
	}
}
