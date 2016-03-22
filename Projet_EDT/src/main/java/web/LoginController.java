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
import business.model.users.Admin;
import business.model.users.Teacher;

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
		email = "admin@admin.admin";
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
	
	public String logout() {
		try {
			manager.managerUsers.logout();
		} catch (IllegalAccessException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(
							FacesMessage.SEVERITY_INFO, 	// This message is info
							null,							// No ID to this message
							"No user connected"));			// Detail of this message
			e.printStackTrace();
		}
		return "login?faces-redirect=true";
	}
	
	public boolean isConnected() {
		return manager.managerUsers.getCurrentUser() != null;
	}
	
	/**
	 * Redirect on index page if connected
	 * This method is used on connection page
	 * @throws IOException
	 */
	public void indexIfConnected() throws IOException {
		if(isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("users.xhtml");
		}
	}
	
	/**
	 * Redirect page on connection Page if not connected
	 * this method should be used on all pages that require to be connected.
	 * @throws IOException
	 */
	public void connectionIfNotConnected() throws IOException {
		if(!isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
		}
	}
	
	/**
	 * Redirect page on connection Page if not connected as Admin
	 * this method should be used on all pages that require to be connected as Admin.
	 * @throws IOException
	 */
	public void accessDeniedIfNotAdmin() throws IOException {
		if(!isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
		} else if(!(manager.managerUsers.getCurrentUser() instanceof Admin)) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("access-denied.xhtml");
		}
	}

	/**
	 * Redirect page on connection Page if not connected as Teacher
	 * this method should be used on all pages that require to be connected as Teacher.
	 * @throws IOException
	 */
	public void accessDeniedIfNotTeacher() throws IOException {
		if(!isConnected()) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
		} else if(!(manager.managerUsers.getCurrentUser() instanceof Teacher)) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect("access-denied.xhtml");
		}
	}
}
