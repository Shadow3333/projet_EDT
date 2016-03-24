package web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.jpa.JpaDao;
import business.exceptions.MalformedParametersException;
import business.manager.AbstractManager;
import business.manager.Manager;
import business.model.Courses;
import business.model.EU;
import business.model.EU.LessonType;
import business.model.GroupEU;
import business.model.GroupStudent;
import business.model.Session;
import business.model.SessionFactory;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Teacher;

@ManagedBean(name = "sessionController")
@SessionScoped
@SuppressWarnings("restriction")
public class SessionController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	Session theSession;
	private int hour;
	
	// Variables for SessionFactory
	private SessionFactory factory;
	private Courses course;
	private Date dateBegin;
	private EU eu;
	private Integer duration;
	private AbstractUser teacher;
	private LessonType type;
	private Integer numGroup;
	private String room;
 
	@PostConstruct
	public void init() {
		System.out.println(this + " created");
		theSession = new Session();
		factory = manager.managerSessions.createFactory();
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	// Getters and Setters for SessionFactory
	public SessionFactory getFactory() {
		return factory;
	}
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	public Courses getCourse() {
		return course;
	}
	public void setCourse(Courses course) {
		this.course = course;
	}
	public Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	public EU getEu() {
		return eu;
	}
	public void setEu(EU eu) {
		this.eu = eu;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public AbstractUser getTeacher() {
		return teacher;
	}
	public void setTeacher(AbstractUser teacher) {
		this.teacher = teacher;
	}
	public LessonType getType() {
		return type;
	}
	public void setType(LessonType type) {
		this.type = type;
	}
	public Integer getNumGroup() {
		return numGroup;
	}
	public void setNumGroup(Integer numGroup) {
		this.numGroup = numGroup;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	// Method for SessionFactory
	public List<Courses> findAllCourses() throws IllegalAccessException, DaoException {
		return manager.managerCourses.findAll();
	}
	public List<EU> getEusForCourse() {
		List<EU> list = new ArrayList<EU>();
		if(course == null) {
			return list;
		}
		list.addAll(course.getObligatories().getEus());
		for(GroupEU g : course.getOptions()) {
			list.addAll(g.getEus());
		}
		return list;
	}
	public List<AbstractUser> getTeachersForEu() throws IllegalAccessException, DaoException {
		List<AbstractUser> teachers = new ArrayList<AbstractUser>();
		if(eu == null || type == null) {
			return teachers;
		}
		AbstractManager<Teacher> teachersManager =
				new AbstractManager<Teacher>(new JpaDao(), manager) {};
		for(Teacher t : teachersManager.findAll()) {
			if((type == null || type.equals(LessonType.CM))
					&& t.getCm().contains(eu)) {
				teachers.add(t);
			} else if((type == null || type.equals(LessonType.TD))
					&& t.getTd().contains(eu)) {
				teachers.add(t);
			} else if((type == null || type.equals(LessonType.TP))
					&& t.getTp().contains(eu)) {
				teachers.add(t);
			}
		}
		return teachers;
	}
	public List<LessonType> getTypeLessons() {
		List<LessonType> list = new ArrayList<LessonType>();
		for(LessonType t : LessonType.values()) {
			list.add(t);
		}
		return list;
	}
	public List<Integer> getGroupsForEU() {
		List<Integer> list = new ArrayList<Integer>();
		if(type == null) {
			return list;
		}
		if(type.equals(LessonType.CM)) {
			return list;
		} else if(type.equals(LessonType.TD)) {
			for(Entry<Integer, GroupStudent> e :
				course.getGroupEUWhoContains(eu).getTd().entrySet()) {
				list.add(e.getKey());
			}
		} else if(type.equals(LessonType.TP)) {
			for(Entry<Integer, GroupStudent> e :
				course.getGroupEUWhoContains(eu).getTp().entrySet()) {
				list.add(e.getKey());
			}
		}
		return list;
	}
	public String saveWithFactory() throws Exception {
		try {
			factory.setCourses(course);
			factory.setDate(dateBegin);
			factory.setIdEU(eu.getId());
			factory.setNbHour(duration);
			factory.setNumGroup(numGroup);
			factory.setRoom(room);
			factory.setTeacher(teacher.getEmail());
			factory.setType(type);
			manager.managerSessions.save(factory.createSession());
		} catch(IllegalAccessException e) {
			return "access-denied?faces-redirect=true";
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			// TODO Feedback for user
			return "";
		} catch(MalformedParametersException e) {
			e.printStackTrace();
			// TODO Feedback for user
			return "";
		}
		reset();
		return "sessions?faces-redirect=true";
	}
	
	public void reset() {
		factory = manager.managerSessions.createFactory();
		course = null;
		dateBegin = null;
		eu = null;
		duration = null;
		teacher = null;
		type = null;
		numGroup = null;
		room = null;
	}
	// END SESSION FACTORY USING

	public String save() throws IllegalAccessException  {
		manager.managerSessions.save(theSession);
		theSession = new Session();
		return "sessions";
	}
	
	public String remove(Session session) throws IllegalAccessException, DaoException {
		manager.managerSessions.remove(session);
		return "sessions";
	}
	
	public String update() throws IllegalAccessException{
		manager.managerSessions.update(theSession);
 		theSession = new Session();
  		return "sessions";
	}
	
	public LessonType[] getLessonType()
	{
		return LessonType.values();
	}
	
	public List<Session> findAll() throws IllegalAccessException, DaoException{
		return manager.managerSessions.findAll();
	}
	
	@SuppressWarnings("deprecation")
	public String show(Session currSession)
	{
		if((manager.managerUsers.getCurrentUser() instanceof Admin)) {
			
			theSession = currSession;
			hour = theSession.getDate().getHours();
			return "editSession?faces-redirect=true";
		} else {
			return "";
		}
	}
	
	public void newSession()
	{
		theSession = new Session();
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
