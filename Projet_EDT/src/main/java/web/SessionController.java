package web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.jpa.JpaDao;
import business.manager.AbstractManager;
import business.manager.Manager;
import business.model.EU;
import business.model.EU.LessonType;
import business.model.Session;
import business.model.users.Teacher;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	Session theSession;
	private int hour;
 
	@PostConstruct
	public void init() {
		System.out.println(this + " created");
		theSession = new Session();
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	public String save() throws IllegalAccessException  {
		manager.managerSessions.save(theSession);
		theSession = new Session();
		return "sessions";
	}
	
	public String remove(Session session) throws IllegalAccessException, DaoException {
		manager.managerSessions.remove(session);
		return "sessions";
	}
	
	public LessonType[] getLessonType()
	{
		return LessonType.values();
	}
	
	public List<Session> findAll() throws IllegalAccessException, DaoException{
		return manager.managerSessions.findAll();
	}
	
//	public List<GroupStudent> findAllGroups(){
//		return sessionM.findAllGroups();
//	}
	
	public List<Teacher> findAllTeachers() throws IllegalAccessException, DaoException{
		AbstractManager<Teacher> teachersManager =
				new AbstractManager<Teacher>(new JpaDao(), manager) {};
		return teachersManager.findAll();
		//return manager.managerSessions.findAllTeachers();
	}
	
	public List<EU> findAllEUS() throws IllegalAccessException, DaoException{
		return manager.managerEus.findAll();
	}
	
	public Session getTheSession() {
		return theSession;
	}

	public void setTheSession(Session theSession) {
		this.theSession = theSession;
	}
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public int getHour() {
		return hour;
	}

	@SuppressWarnings("deprecation")
	public void setHour(int hour) {
		this.hour = hour;
		theSession.getDate().setHours(hour);
	}

}
