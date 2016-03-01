package business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;

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
	@Column(nullable = false)
	private LessonType groupType;
	@Column(nullable = true)
	@Valid
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

	/**
	 * Add an <code>AbstractUser</code> to students list
	 * @param student
	 * @return
	 */
	public boolean addStudent(AbstractUser student) {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(students == null) {
			students = new ArrayList<AbstractUser>();
		}
		return students.add(student);
	}
	
	/**
	 * Remove an <code>AbstractUser</code> from students list
	 * @param student
	 * @return
	 */
	public boolean removeStudent(AbstractUser student) {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		return students.remove(student);
	}
}
