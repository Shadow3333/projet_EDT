package web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import business.dao.DaoException;
import business.dao.IDao;
import business.dao.jpa.JpaDao;
import business.manager.Manager;
import business.manager.ManagerCourses;
import business.model.Courses;
import business.model.EU;
import business.model.GroupEU;
/**
 * 
 * @author
 *
 */
@ManagedBean(name = "EducationalBackgroundController")
@SessionScoped
public class EducationalBackgroundController {

	Manager manager;
	EUsController euM;
	Courses theEducationalBackground;
	List<EU> optionals;
	
	public EducationalBackgroundController (){
		// TODO @autowired
		IDao dao = new JpaDao();
		manager = new Manager(dao);
		euM = new EUsController();
		init();
	}

	public String save() throws IllegalAccessException {
		GroupEU tempo;
		for (EU eu : optionals) {
			tempo = new GroupEU();
			tempo.addEU(eu);
			theEducationalBackground.addOptions(tempo);
		}
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
		return euM.findAll();
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public EUsController getEuM() {
		return euM;
	}

	public void setEuM(EUsController euM) {
		this.euM = euM;
	}

	public List<EU> getOptionals() {
		return optionals;
	}

	public void setOptionals(List<EU> optionals) {
		this.optionals = optionals;
	}
	
	public void init()
	{
		theEducationalBackground = new Courses();
		GroupEU gEU = new GroupEU();
		gEU.setEus(new ArrayList<EU>());
		theEducationalBackground.setObligatories(gEU);
		optionals = new ArrayList<EU>();
	}
	
}