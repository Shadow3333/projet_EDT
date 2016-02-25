package business.model;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * EU describes a teaching unit
 * @author DUBUIS Michael
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
			name="findAll",
			query="SELECT e FROM EU e")
})
public class EU {
	public static enum LessonType {
		TD,
		TP,
		CM,
		Other
	}
	
	@Id
	private String id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private Integer nbCredits;
	@ElementCollection
	@CollectionTable(
			name = "EUHoursByLessonType",
			joinColumns = @JoinColumn(name="IdEU"))
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "LessonType")
	@Column(name = "Hours")
	private Map<LessonType, Integer> nbHours;
	
	/**
	 * Empty constructor
	 */
	public EU() {}

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the nbCredits
	 */
	public Integer getNbCredits() {
		return nbCredits;
	}

	/**
	 * @param nbCredits the nbCredits to set
	 */
	public void setNbCredits(Integer nbCredits) {
		this.nbCredits = nbCredits;
	}

	/**
	 * @return the nbHours
	 */
	public Map<LessonType, Integer> getNbHours() {
		return nbHours;
	}

	/**
	 * @param nbHours the nbHours to set
	 */
	public void setNbHours(Map<LessonType, Integer> nbHours) {
		this.nbHours = nbHours;
	}
	
	// TODO UTILS TOOLS FOR EU (ADDERS & REMOVERS)
}
