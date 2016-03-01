package business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

/**
 * Courses presents every possible courses.
 * This class is used during pedagogic registration and session creation
 * @author DUBUIS Michael
 *
 */
@Entity
public class Courses {
	@Id
	private String id;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	@Valid
	private GroupEU obligatories;
	@ManyToMany
	@CollectionTable(
			name = "CoursesListOptions",
			joinColumns = @JoinColumn(name = "CoursesId"))
	@Valid
	private List<GroupEU> options;
	
	/**
	 * Empty constructor
	 */
	public Courses() {}
	
	/**
	 * Constructor with parameter id
	 * @param id
	 */
	public Courses(String id) {
		this.id = id;
	}
	
	/**
	 * Constructor with parameters id and name
	 * @param id
	 * @param name
	 */
	public Courses(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the obligatories
	 */
	public GroupEU getObligatories() {
		return obligatories;
	}

	/**
	 * @param obligatories the obligatories to set
	 */
	public void setObligatories(GroupEU obligatories) {
		this.obligatories = obligatories;
	}

	/**
	 * @return the options
	 */
	public List<GroupEU> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<GroupEU> options) {
		this.options = options;
	}
	
	// TOOLS
	/**
	 * Return all EU for this courses
	 * @return
	 */
	public List<EU> getEUs() {
		List<EU> eus = new ArrayList<EU>();
		eus.addAll(getEUs(true));
		eus.addAll(getEUs(false));
		return eus;
	}
	
	/**
	 * Return all EU optional or obligatory depend of parameter 
	 * @param option
	 * @return
	 */
	public List<EU> getEUs(boolean optional) {
		List<EU> eus = new ArrayList<EU>();
		if(optional) {
			if(this.getOptions() != null){
				for(GroupEU g : this.getOptions()) {
					eus.addAll(g.getEus());
				}
			}
		} else {
			if(this.getObligatories() != null) {
				eus.addAll(this.getObligatories().getEus());
			}
		}
		return eus;
	}

	/**
	 * Return the GroupEU which contains EU
	 * @param eu
	 * @return
	 */
	public GroupEU getGroupEUWhoContains(EU eu) {
		if(this.getObligatories().getEus().contains(eu)) {
			return this.getObligatories();
		}
		for(GroupEU g : getOptions()) {
			if(g.getEus().contains(eu)) {
				return g;
			}
		}
		return null;
	}
	
	/**
	 * Add a groupEU to options
	 * @param groupEU
	 * @return true if added, false else
	 * @throws IllegalArgumentException - if groupEU is null
	 */
	public boolean addOptions(GroupEU groupEU)
			throws IllegalArgumentException{
		if(groupEU == null) {
			throw new IllegalArgumentException(
					"groupEU must be different than null !");
		}
		if(options == null) {
			options = new ArrayList<GroupEU>();
		}
		return options.add(groupEU);
	}

	/**
	 * Remove a groupEU to options
	 * @param groupEU
	 * @return true if removed, false else
	 * @throws IllegalArgumentException - if groupEU is null
	 */
	public boolean removeOptions(GroupEU groupEU)
			throws IllegalArgumentException{
		if(groupEU == null) {
			throw new IllegalArgumentException(
					"groupEU must be different than null !");
		}
		return options.remove(groupEU);
	}
}
