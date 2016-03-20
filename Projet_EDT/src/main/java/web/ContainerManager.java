package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.manager.Manager;

/**
 * @author DUBUIS Michael
 *
 */
@ManagedBean
@SessionScoped
public class ContainerManager {
	
	private Manager manager;
	
	@PostConstruct
	public void init() {
		IDao dao = new JpaDao();
		manager = new Manager(dao);
		System.out.println(manager + " created");
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
