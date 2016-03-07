package web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import business.dao.DaoException;

/**
 * This is an example of ManagedBean
 * Associated jsf page : login.xhtml
 * @author DUBUIS Michael
 *
 */
@ManagedBean
@SessionScoped
public class LoginBean extends AbstractManagedBean {
	private String email;
	private String password;
	
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
				return "home?faces-redirect=true";
			}
		} catch (DaoException e) {
			// TODO add message if DaoException ? 
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
