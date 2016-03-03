package business.manager;

import business.dao.IDao;
import business.model.GroupEU;
import business.model.users.AbstractUser;
import business.model.users.Admin;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 */
public class ManagerGroupEU extends AbstractManager<GroupEU> {

	public ManagerGroupEU(IDao dao) {
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
	
	@Override
	public boolean canUpdate(AbstractUser user) {
		return true;
	}

}
