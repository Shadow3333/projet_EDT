package web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.manager.Manager;
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
@SuppressWarnings("restriction")
public class EducationalBackgroundController {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

	Courses theEducationalBackground;
	List<EU> optionals;
	GroupEU mandatories;

	@PostConstruct
	public void init() {
		reset();
		System.out.println(this + " created");
	}
	
	@PreDestroy
	public void close() {
		System.out.println(this + " destroyed");
	}

	public Manager getManager() {
		return manager;
	}
	
	public void reset() {
		theEducationalBackground = new Courses();
		if(theEducationalBackground.getObligatories() == null) {
			GroupEU groupEUObligatories = new GroupEU();
			groupEUObligatories.setOptionnal(false);
			groupEUObligatories.setEus(new ArrayList<EU>());
			theEducationalBackground.setObligatories(groupEUObligatories);
		}
		optionals = new ArrayList<EU>();
	}
	
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String save() throws IllegalAccessException {
		manager.managergroupEU.save(mandatories);
		theEducationalBackground.setObligatories(mandatories);
		for (EU eu : optionals) {
			GroupEU groupEUOptionnal;
			groupEUOptionnal = new GroupEU();
			groupEUOptionnal.setOptionnal(true);
			groupEUOptionnal.addEU(eu);
			theEducationalBackground.addOptions(groupEUOptionnal);
		}
		
		manager.managerCourses.save(theEducationalBackground);
		reset();
		return "educationalBackgrounds?faces-redirect=true";
	}
	
	public String remove(Courses educationalBackground) throws IllegalAccessException, DaoException {
		manager.managerCourses.remove(educationalBackground);
		return "educationalBackgrounds";
	}
	
	public String update() throws IllegalAccessException
	{
		manager.managerCourses.update(theEducationalBackground);
		return "educationalBackgrounds?faces-redirect=true";
	}
	
	public String show(Courses eb)
	{
		theEducationalBackground = eb;
		for (GroupEU gEU : eb.getOptions()) {
			optionals.addAll(gEU.getEus());
		}
		mandatories = eb.getObligatories();
		return "editEducationalBackground?faces-redirect=true";
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

	public GroupEU getMandatories() {
		if(mandatories == null) {
			mandatories = new GroupEU();
		}
		return mandatories;
	}

	public void setMandatories(GroupEU mandatories) {
		this.mandatories = mandatories;
	}
}