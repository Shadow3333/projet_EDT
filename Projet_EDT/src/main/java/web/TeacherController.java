package web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.manager.Manager;
import business.model.users.AbstractUser;
import business.model.users.Admin;
import business.model.users.Teacher;

@ManagedBean(name = "teacherController")
@SessionScoped
public class TeacherController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
	
	Teacher theTeacher;
	
	
	@PostConstruct
	public void init(){
		theTeacher = new Teacher();
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
	
	public String save() throws IllegalAccessException {
 		manager.managerUsers.save(theTeacher);
 		return "users";
 	}
	
	public String editGroups(AbstractUser teacher)
	{
		if((manager.managerUsers.getCurrentUser() instanceof Admin)
				|| manager.managerUsers.getCurrentUser().equals(teacher)) {
			theTeacher = (Teacher) teacher;
			return "teacherEUs?faces-redirect=true";
		} else {
			return "";
		}
	}

	public Teacher getTheTeacher() {
		return theTeacher;
	}

	public void setTheTeacher(Teacher theTeacher) {
		this.theTeacher = theTeacher;
	}

}
