package web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.manager.Manager;
import business.model.EU;


/**
 * 
 * @author
 *
 */
@ManagedBean(name = "eusController")
@SessionScoped
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
	
	public String remove(EU ue) throws IllegalAccessException, DaoException {
		manager.managerEus.remove(ue);
		return "eus";
	}
		
	
	public List<EU> findAll() throws IllegalAccessException, DaoException{
		return manager.managerEus.findAll();
	}
	
	public EU getTheEU() {
		return theEU;
	}

	public void setTheEU(EU theEU) {
		this.theEU = theEU;
	}
	
}