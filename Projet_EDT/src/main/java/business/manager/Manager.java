package business.manager;

import org.springframework.beans.factory.annotation.Autowired;

import business.dao.IDao;

/**
 * @author DUBUIS Michael
 *
 */
public class Manager {
	@Autowired
	IDao dao;
	
	public ManagerCourses managerCourses;
	public ManagerEUs managerEus;
	public ManagerGroupEU managergroupEU;
	public ManagerGroupStudent managerGroupStudent;
	public ManagerSessions managerSessions;
	public ManagerUsers managerUsers;
	
	/**
	 * 
	 */
	public Manager() {
		managerCourses = new ManagerCourses(dao);
		managerEus = new ManagerEUs(dao);
		managergroupEU = new ManagerGroupEU(dao);
		managerGroupStudent = new ManagerGroupStudent(dao);
		managerSessions = new ManagerSessions(dao);
		managerUsers = new ManagerUsers(dao);
	}

}
