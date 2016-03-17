package web;

import java.util.List;

import javax.faces.bean.ManagedBean;
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
@ManagedBean(name = "euController")
@SessionScoped
public class EUsController {

	Manager manager;
	EU theEU;
	
	public EUsController (){
		// TODO @autowired
		IDao dao = new JpaDao();
		manager = new Manager(dao);
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
	
	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public EU getTheEU() {
		return theEU;
	}

	public void setTheEU(EU theEU) {
		this.theEU = theEU;
	}
	
}