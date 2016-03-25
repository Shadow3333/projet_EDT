package web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.manager.Manager;
import business.model.EU;
import business.model.GroupEU;


/**
 * 
 * @author
 *
 */
@ManagedBean(name = "eusController")
@SessionScoped
@SuppressWarnings("restriction")
public class EUsController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

	EU theEU;

	@PostConstruct
	public void init() {
		System.out.println(this + " created");
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public EUsController (){
		theEU = new EU();
	}

	public String save() throws IllegalAccessException {
		manager.managerEus.save(theEU);
		theEU = new EU();
		return "eus";
	}
	
	public String show(EU eu)
	{
		theEU = eu;
		return "editEU?faces-redirect=true";
	}
	
	public void newEU()
	{
		theEU = new EU();
	}
	
	public String update() throws IllegalAccessException
	{
		manager.managerEus.remove(theEU.getId());
		manager.managerEus.save(theEU);
		return "eus";
	}
	
	public String remove(EU ue) throws IllegalAccessException, DaoException {
		manager.managerEus.remove(ue);
		return "eus";
	}
		
	
	public List<EU> findAll() throws IllegalAccessException, DaoException{
		List<EU> list = new ArrayList<EU>();
		list.addAll(manager.managerEus.findAll());
		list.sort(new Comparator<EU>() {
			public int compare(EU o1, EU o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		return list;
	}
	
	public EU getTheEU() {
		return theEU;
	}

	public void setTheEU(EU theEU) {
		this.theEU = theEU;
	}
	
}