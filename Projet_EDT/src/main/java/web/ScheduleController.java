package web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.PostLoad;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import business.dao.DaoException;
import business.manager.Manager;
import business.model.Session;

@ManagedBean(name = "ScheduleController")
@SessionScoped
public class ScheduleController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	private ScheduleModel eventModel;
	ScheduleEvent event = new DefaultScheduleEvent();

	@PostConstruct
    public void init() {
		System.out.println(this + " created");
        eventModel = new DefaultScheduleModel();
        
//        List<Session> listSession  = findAllSessions();
//        for (Session currSession : listSession) {
//			addEvent(currSession);
//		}
        
    }
	
	@PostLoad
	 public void load() throws IllegalAccessException, DaoException {
		System.out.println("kikou");
        List<Session> listSession  = findAllSessions();
        for (Session currSession : listSession) {
			addEvent(currSession);
		}
        
    }
	
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	public List<Session> findAllSessions() throws IllegalAccessException, DaoException{
		return manager.managerSessions.findAll();
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
	
	public void addEvent(Session currSession)
	{
		String text = "Teaching unit : " + currSession.getEu().getName() + "\n" +
					  "Teacher : " + currSession.getTeacher().getLastName() + "\n"+
					  currSession.getType();
		eventModel.addEvent(new DefaultScheduleEvent(text, currSession.getDate(),
													 getDateplusDuration(currSession)));
	}
	
	@SuppressWarnings("deprecation")
	private Date getDateplusDuration(Session currSession)
	{
		Date date = currSession.getDate();
		date.setHours(date.getHours()+currSession.getNbHour());
		return date;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
