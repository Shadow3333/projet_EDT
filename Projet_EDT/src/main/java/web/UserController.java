package web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.manager.Manager;
import business.model.Courses;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Student;
import business.model.users.Teacher;
import web.util.Role;

@ManagedBean(name = "UserController")
@SessionScoped
public class UserController {
	
	Manager manager;
	AbstractUser theUser;
	Courses eb;
	String role;
	
	List<AbstractUser> users = new ArrayList<AbstractUser>();
	
	public UserController(){
		// TODO @autowired
		IDao dao = new JpaDao();
		theUser = new Admin();
		manager = new Manager(dao);
		eb = new Courses();
	}
	
	public String save() throws IllegalAccessException
	{
		AbstractUser user;
		switch (getUserType()) {
		case Admin:
			user = new Admin();
			break;
			
		case Teacher:
			user = new Teacher();
			break;
				
		case Student:
			user = new Student();
			break;
		
		default:
			//gestion err
			return "";
		}
		user.setEmail(theUser.getEmail());
		user.setHashPwd(theUser.getHashPwd());
		user.setFirstName(theUser.getFirstName());
		user.setLastName(theUser.getLastName());
		user.setBirthDate(theUser.getBirthDate());
		user.setWebSite(theUser.getWebSite());
		user.setPhones(theUser.getPhones());
		manager.managerUsers.save(user);
		theUser = new Admin();
		return "users";
	}
	
	public String update() throws IllegalAccessException
	{
		
		manager.managerUsers.update(theUser);
		return "users";
	}
	
	public String show(AbstractUser user)
	{
		theUser = user;
		return "editUser";
	}
	
	public void remove(AbstractUser user) throws IllegalAccessException, DaoException{
		manager.managerUsers.remove(user);
	}
	
	public List<String> getRoles()
    {
    	List<String> myList = new ArrayList<String>();
    	for (Role currRole : Role.values()) {
			myList.add(currRole.toString());
		}
    	return myList;
    }
	
	public List<Courses> findAllEBs() throws IllegalAccessException, DaoException
	{
		return manager.managerCourses.findAll();
	}
	
	public String doER(AbstractUser user)
	{
		theUser = user;
		return "educationalRegistration";
	}

	
	public List<AbstractUser> findAll() throws IllegalAccessException, DaoException
	{
		return manager.managerUsers.findAll();
	}
	
	public Role getUserType()
	{
		return Role.valueOf(role);
	}
	
	public String getFormattedDate(Date date)
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");//dd/MM/yyyy
	    return sdfDate.format(date);
	}
	
	public String getRole()
	{
		return role;
	}
	
	public AbstractUser newUser()
	{
		theUser = new Admin();
		return theUser;
	}
	
	public String getRole(AbstractUser user)
	{
		return user.getClass().getSimpleName();
	}

	public void setRole(String role) {
		this.role = role;
	}

	public AbstractUser getTheUser() {
		return theUser;
	}

	public void setTheUser(AbstractUser theUser) {
		this.theUser = theUser;
	}

	public Courses getEb() {
		return eb;
	}

	public void setEb(Courses eb) {
		this.eb = eb;
	}
	
	/***
	 * This method returns a person's with no mail.
	 * @return a person
	 */
//	@ModelAttribute("person")
//	public User getMail() {
//		User p = new User();
//		return p;
//	}

	
}