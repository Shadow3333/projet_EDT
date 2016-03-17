package web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import business.manager.Manager;
import business.model.Session;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController {
	
	Manager manager;
	Session theSession;
	
	

}
