package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.manager.Manager;
import business.model.Session;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	Session theSession;

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

}
