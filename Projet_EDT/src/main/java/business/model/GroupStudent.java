package business.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.model.EU.LessonType;
import business.model.users.AbstractUser;

/**
 * GroupStudent is a group for tutorial, practical works or lectures for a <code>GroupEU</code>.
 * @author DUBUIS Michael
 *
 */
@Entity
public class GroupStudent {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private LessonType groupType;
	@Column
	private List<AbstractUser> students;
	
	/**
	 * Empty constructor
	 */
	public GroupStudent() {}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the groupType
	 */
	public LessonType getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(LessonType groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the students
	 */
	public List<AbstractUser> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<AbstractUser> students) {
		this.students = students;
	}
	
	// TODO UTILS TOOLS FOR GROUPSTUDENT (ADDERS & REMOVERS)
}
