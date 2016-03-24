package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.manager.Manager;
import business.model.users.AbstractUser;

/**
 * @author DUBUIS Michael
 *
 */
@ManagedBean(name = "studentController")
@SessionScoped
@SuppressWarnings("restriction")
public class StudentController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	AbstractUser theUser;
	
	@PostConstruct
	public void init(){
		System.out.println(this + " created");
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	public String loggedUserEducationalRegistration() {
		theUser = manager.managerUsers.getCurrentUser();
		return "educationalRegistration?faces-redirect=true";
	}
}
