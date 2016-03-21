package web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.exceptions.PedagogicRegistrationException;
import business.manager.Manager;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Student;
import business.model.users.Teacher;
import web.util.Role;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	AbstractUser theUser;
	Courses eb;
	String role;
	
	List<EU> optionals;
	List<AbstractUser> users;
	
	@PostConstruct
	public void init(){
		theUser = new Admin();
		eb = new Courses();
		users = new ArrayList<AbstractUser>();
		optionals = new ArrayList<EU>();
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
	
	public String save() throws IllegalAccessException
  	{
 		AbstractUser user = roleToObject();
 		manager.managerUsers.save(user);
 		theUser = new Admin();
 		return "users";
 	}
	
	public AbstractUser roleToObject()
	 	{
	 		AbstractUser user = null;
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
	 			break;
	  		}
	 		
	 		user.setEmail(theUser.getEmail());
	 		user.setPassword(theUser.getHashPwd());
	 		user.setFirstName(theUser.getFirstName());
	 		user.setLastName(theUser.getLastName());
	 		user.setBirthDate(theUser.getBirthDate());
	 		user.setWebSite(theUser.getWebSite());
	 		user.setPhones(theUser.getPhones());
	 		return user;
	  	}
	
	public String update() throws IllegalAccessException
	{
		
	AbstractUser user = roleToObject();
		manager.managerUsers.update(user);
 		theUser = new Admin();
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
	
	public List<Courses> findAllEBs() throws PedagogicRegistrationException, IllegalAccessException, DaoException
  	{
 		if (theUser == null) {
 			throw new PedagogicRegistrationException();
 		}
  		List<Courses> list = manager.managerCourses.findAll();
  		if (list.isEmpty() == false) {
  			eb = list.get(0);
 		}
 		return list;
 	}
	
	public List<EU> getEBOptionals()
    {
    	return eb.getEUs();
    }
	
	public String doER(AbstractUser user)
	{
		theUser = user;
		return "educationalRegistration";
	}

	public String saveER() throws PedagogicRegistrationException
	{
		
		if (theUser == null) {
			optionals = new ArrayList<EU>();
			return "users";
		}
		if (theUser.getClass().equals(Student.class)) {
			List<GroupEU> listGrEU = new ArrayList<GroupEU>();
			listGrEU.add(eb.getObligatories());
			GroupEU tempo;
			for (EU eu : optionals) {
				tempo = new GroupEU();
				tempo.addEU(eu);
				listGrEU.add(tempo);
			}
			((Student)theUser).setGroups(listGrEU);
			((Student)theUser).setIdCourses(eb.getId());
			optionals = new ArrayList<EU>();
			return "users";
		}
		optionals = new ArrayList<EU>();
		return "errUser";
		
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
