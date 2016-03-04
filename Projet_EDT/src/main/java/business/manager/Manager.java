package business.manager;

import business.dao.IDao;

/**
 * @author DUBUIS Michael
 *
 */
public class Manager {
	IDao dao;
	
	public ManagerCourses managerCourses;
	public ManagerEUs managerEus;
	public ManagerGroupEU managergroupEU;
	public ManagerGroupStudent managerGroupStudent;
	public ManagerSessions managerSessions;
	public ManagerUsers managerUsers;
	
	/**
	 * Empty constructor
	 * @deprecated
	 */
	public Manager() {
	}
	
	public Manager(IDao dao) {
		this.dao = dao;
		managerCourses = new ManagerCourses(this.dao, this);
		managerEus = new ManagerEUs(this.dao, this);
		managergroupEU = new ManagerGroupEU(this.dao, this);
		managerGroupStudent = new ManagerGroupStudent(this.dao, this);
		managerSessions = new ManagerSessions(this.dao, this);
		managerUsers = new ManagerUsers(this.dao, this);
	}
}
