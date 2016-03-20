package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import business.dao.DaoException;
import business.manager.Manager;

/**
 * This is an example of ManagedBean
 * Associated jsf page : login.xhtml
 * @author DUBUIS Michael
 *
 */
@ManagedBean
@SessionScoped
public class LoginBean {
	@ManagedProperty(value="#{containerManager}")
	private ContainerManager containerManager;
	
	private String email;
	private String password;
	

	@PostConstruct
	public void init() {
		System.out.println(this + " created");
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the containerManager
	 */
	public ContainerManager getContainerManager() {
		return containerManager;
	}

	/**
	 * @param containerManager the containerManager to set
	 */
	public void setContainerManager(ContainerManager containerManager) {
		this.containerManager = containerManager;
	}

	/**
	 * To connect an user who exists in Database
	 * @return Page to load
	 */
	public String login() {
		try {
			if(containerManager.getManager().managerUsers.login(email, password)) {
				// If login ok, redirect home page
				System.out.println("Connection ok");
				return "users?faces-redirect=true";
			}
		} catch (DaoException e) {
			// TODO add message if DaoException ?
			e.printStackTrace();
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(
						FacesMessage.SEVERITY_INFO, 	// This message is info
						null,							// No ID to this message
						"Email or password is wrong"));	// Detail of this message
		return "";
	}
}
