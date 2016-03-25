package business.manager;

import business.dao.IDao;
import business.model.GroupStudent;

/**
 * @author LELIEVRE Romain
 * @contributor DUBUIS Michael
 */
public class ManagerGroupStudent extends AbstractManager<GroupStudent> {

	public ManagerGroupStudent(IDao dao, Manager manager) {
		super(dao, manager);
	}
}
