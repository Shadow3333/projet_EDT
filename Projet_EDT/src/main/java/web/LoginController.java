package web;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
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
public class LoginController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
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

	public Manager getManager() {
		return manager;
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
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
	 * To connect an user who exists in Database
	 * @return Page to load
	 */
	public String login() {
		try {
			if(manager.managerUsers.login(email, password)) {
				// If login ok, redirect home page
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(
								FacesMessage.SEVERITY_INFO, 	// This message is info
								null,							// No ID to this message
								"Connection ok"));	// Detail of this message
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
	
	public boolean isConnected() {
		return manager.managerUsers.getCurrentUser() != null;
	}
	
	public String indexIfConnected() throws IOException {
		if(isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("users.xhtml");
		}
		return "";
	}
	
	public String connectionIfNotConnected() throws IOException {
		if(!isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
		}
		return "";
	}
}
