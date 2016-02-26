package business.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import business.model.EU.LessonType;
import business.model.users.AbstractUser;

/**
 * @author DUBUIS Michael
 *
 */
@Entity
public class Session {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = true)
	private GroupStudent groupStudent;
	@Column(nullable = true)
	private EU eu;
	@Column(nullable = true)
	private AbstractUser teacher;
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private LessonType type;
	@Column(nullable = false)
	@Min(value = 1)
	private Integer nbHour;
	
	/**
	 * Empty constructor
	 */
	private Session() {}

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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the groupStudent
	 */
	public GroupStudent getGroupStudent() {
		return groupStudent;
	}

	/**
	 * @param groupStudent the groupStudent to set
	 */
	public void setGroupStudent(GroupStudent groupStudent) {
		this.groupStudent = groupStudent;
	}

	/**
	 * @return the eu
	 */
	public EU getEu() {
		return eu;
	}

	/**
	 * @param eu the eu to set
	 */
	public void setEu(EU eu) {
		this.eu = eu;
	}

	/**
	 * @return the teacher
	 */
	public AbstractUser getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(AbstractUser teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the type
	 */
	public LessonType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(LessonType type) {
		this.type = type;
	}

	/**
	 * @return the nbHour
	 */
	public Integer getNbHour() {
		return nbHour;
	}

	/**
	 * @param nbHour the nbHour to set
	 */
	public void setNbHour(Integer nbHour) {
		this.nbHour = nbHour;
	}
}
