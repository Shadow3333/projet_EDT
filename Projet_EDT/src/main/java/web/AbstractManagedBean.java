package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import business.dao.IDao;
import business.manager.Manager;

/**
 * All Managed Bean must extends this class to have access to <code>Manager</code>
 * @author DUBUIS Michael
 *
 */
@ManagedBean
@SessionScoped
public abstract class AbstractManagedBean {
	@Autowired
	protected IDao dao;
	
	protected Manager manager;
	
	@PostConstruct
	public void init() {
		manager = new Manager(dao);
		System.out.println("Create " + this);
	}
	
	@PreDestroy
	public void close() {
		System.out.println("Close " + this);
	}
}
