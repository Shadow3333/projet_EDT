package web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.manager.Manager;
import business.manager.ManagerCourses;
import business.manager.ManagerEUs;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
/**
 * 
 * @author
 *
 */
@ManagedBean(name = "educationalBackgroundController")
@SessionScoped
public class EducationalBackgroundController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

	Courses theEducationalBackground;
	List<EU> optionals;

	@PostConstruct
	public void init() {
		theEducationalBackground = new Courses();
		GroupEU gEU = new GroupEU();
		gEU.setEus(new ArrayList<EU>());
		theEducationalBackground.setObligatories(gEU);
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

	public String save() throws IllegalAccessException {
		GroupEU tempo;
		for (EU eu : optionals) {
			tempo = new GroupEU();
			tempo.addEU(eu);
			theEducationalBackground.addOptions(tempo);
			manager.managergroupEU.save(tempo);
		}
//		System.out.println(theEducationalBackground);
//		System.out.println(theEducationalBackground.getId());
//		System.out.println(theEducationalBackground.getName());
//		System.out.println(theEducationalBackground.getEUs().toString());
//		System.out.println("-----------------------------");
//		System.out.println(theEducationalBackground.getObligatories().getEus().get(0).getName());
		
		manager.managergroupEU.save(theEducationalBackground.getObligatories());
		manager.managerCourses.save(theEducationalBackground);
		init();
		return "educationalBackgrounds";
	}
	
	public String remove(Courses educationalBackground) throws IllegalAccessException, DaoException {
		manager.managerCourses.remove(educationalBackground);
		return "educationalBackgrounds";
	}
	
	public String update() throws IllegalAccessException
	{
		manager.managerCourses.update(theEducationalBackground);
		return "educationalBackgrounds";
	}
	
	public String show(Courses eb)
	{
		theEducationalBackground = eb;
		for (GroupEU gEU : eb.getOptions()) {
			optionals.addAll(gEU.getEus());
		}
		return "editEducationalBackground";
	}
		
	public List<EU> findAllEUs() throws IllegalAccessException, DaoException
	{
		return manager.managerEus.findAll();
	}

	public List<Courses> findAll() throws IllegalAccessException, DaoException
	{
		return manager.managerCourses.findAll();
	}
	
	public Courses getTheEducationalBackground() {
		return theEducationalBackground;
	}

	public void setTheEducationalBackground(Courses theEducationalBackground) {
		this.theEducationalBackground = theEducationalBackground;
	}

	public List<EU> getOptionals() {
		return optionals;
	}

	public void setOptionals(List<EU> optionals) {
		this.optionals = optionals;
	}
}