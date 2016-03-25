package web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.exceptions.PedagogicRegistrationException;
import business.manager.Manager;
import business.model.Courses;
import business.model.GroupEU;
import business.model.users.AbstractUser;
import business.model.users.Student;

/**
 * @author DUBUIS Michael
 *
 */
@ManagedBean(name="studentController")
@SessionScoped
@SuppressWarnings("restriction")
public class StudentController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	String email;
	
	Courses course;
	List<GroupEU> optionals;
	List<GroupEU> selectedOptionals;
	GroupEU mandatories;
	
	@PostConstruct
	public void init(){
		System.out.println(this + " created");
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}
	

	public List<Courses> findAllCourses()
			throws IllegalAccessException,
			DaoException {
 		return manager.managerCourses.findAll();
 	}
	
	public String redirectRegistration() throws IllegalAccessException, DaoException {
		return redirectRegistration(manager.managerUsers.getCurrentUser());
	}
	
	public String redirectRegistration(AbstractUser user) throws IllegalAccessException, DaoException {
		email = user.getEmail();
		if(!(user instanceof Student)) {
			return "";
		}
		if(((Student) user).getIdCourses() != null) {
			course = manager.managerCourses.find(((Student) user).getIdCourses());
			selectedOptionals = new ArrayList<GroupEU>();
		} else {
			course = findAllCourses().get(0);
		}
		return "educationalRegistration?email="+email;
	}
	
	public String register() throws PedagogicRegistrationException, IllegalAccessException, DaoException {
		AbstractUser student = manager.managerUsers.find(email);
		if (student == null) {
			return "users?faces-redirect=true";
		}
		if(!(student instanceof Student)) {
			return "users?faces-redirect=true";
		}
		course = manager.managerCourses.find(course.getId());
		// add the current student to CM, TD and TP for mandatories EU
		System.out.println("Before : " + course.getObligatories().getEus().size());
		course.getObligatories().addUserToCM(student);
		course.getObligatories().addUserToTD(student);
		course.getObligatories().addUserToTP(student);
		System.out.println("After create group student : " + course.getObligatories().getEus().size());
		manager.managergroupEU.save(course.getObligatories());
		System.out.println("After save groupEU : " + course.getObligatories().getEus().size());
		((Student)student).getGroups().add(course.getObligatories());
		System.out.println("After add group to student : " + course.getObligatories().getEus().size());
		for (GroupEU groupEUForOption : optionals) {
			if(groupEUForOption == null) {
				// Problem with pedagogic registration
				System.err.println(
						" not in university course " +
						course.getId());
				throw new PedagogicRegistrationException(
						" not in university course " +
						course.getId());
			}
			
			// adding the current student to CM, TD and TP for this optional EU
			groupEUForOption.addUserToCM(student);
			groupEUForOption.addUserToTD(student);
			groupEUForOption.addUserToTP(student);
			manager.managergroupEU.save(groupEUForOption);
			((Student)student).getGroups().add(groupEUForOption);
		}
		((Student)student).setIdCourses(course.getId());
		manager.managerUsers.save(student);
		manager.managerCourses.save(course);
		
		// Redirect depends who is connected
		if(manager.managerUsers.getCurrentUser() instanceof Student) {
			return "schedule?faces-redirect=true";
		} else {
			return "users?faces-redirect=true";
		}
	}
	
	//// GETTERS AND SETTERS
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public List<GroupEU> getSelectedOptionals() {
		return selectedOptionals;
	}

	public void setSelectedOptionals(List<GroupEU> selectedOptionals) {
		this.selectedOptionals = selectedOptionals;
	}

	public GroupEU getMandatories() {
		return course.getObligatories();
	}
	
	public void setMandatories(GroupEU manadatories) {
		System.out.println(manadatories.getEus().size());
		this.mandatories = manadatories;
	}
	
	public List<GroupEU> getOptionals(){
		if(optionals == null) {
			return new ArrayList<GroupEU>();
		}
    	return optionals;
    }
	
	public void setOptionals(List<GroupEU> optionals) {
		this.optionals = optionals;
	}
}
