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

/**
 * EU describes a teaching unit
 * @author DUBUIS Michael
 *
 */
@Entity
public class EU {
	public static enum LessonType {
		TD,
		TP,
		CM,
		Other
	}
	
	@Id
	private String id;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private String description;
	@Column(nullable = true)
	private Integer nbCredits;
	@ElementCollection
	@CollectionTable(
			name = "EUHoursByLessonType",
			joinColumns = @JoinColumn(name="IdEU"))
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "LessonType")
	@Column(
			name = "Hours",
			nullable = true)
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
	
//	@Override
//	public boolean equals(Object obj) {
//		if(!(obj instanceof EU)) {
//			return false;
//		}
//		EU e = (EU) obj;
//		return e.getId().equals(this.getId());
//	}
	
	public boolean equals(Object other) {
        return other instanceof EU && (id != null) ? id.equals(((EU) other).id) : (other == this);
    }

    // This must return the same hashcode for every Foo object with the same key.
    public int hashCode() {
        return id != null ? this.getClass().hashCode() + id.hashCode() : super.hashCode();
    }

    // Override Object#toString() so that it returns a human readable String representation.
    // It is not required by the Converter or so, it just pleases the reading in the logs.
    public String toString() {
        return "EU";
    }
	// TODO UTILS TOOLS FOR EU (ADDERS & REMOVERS)
}
