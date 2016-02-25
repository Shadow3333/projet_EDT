package business.model.users;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import business.model.GroupEU;

/**
 * Student extends AbsctractUser. This class is used to describe an user with student's rights.
 * @author DUBUIS Michael
 *
 */
@Entity
public class Student extends AbstractUser {
	@Column
	private String idCourses;
	@ElementCollection
	@CollectionTable(
			name = "StudentGroupsEU",
			joinColumns = @JoinColumn(name = "UserEmail"))
	private List<GroupEU> groups;
	
	/**
	 * Empty Constructor
	 */
	public Student() {
		super();
	}

	/**
	 * @return the idCourses
	 */
	public String getIdCourses() {
		return idCourses;
	}

	/**
	 * @param idCourses the idCourses to set
	 */
	public void setIdCourses(String idCourses) {
		this.idCourses = idCourses;
	}

	/**
	 * @return the groups
	 */
	public List<GroupEU> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<GroupEU> groups) {
		this.groups = groups;
	}
	
	// TODO UTILS TOOLS FOR STUDENT (ADDERS & REMOVERS)
}
