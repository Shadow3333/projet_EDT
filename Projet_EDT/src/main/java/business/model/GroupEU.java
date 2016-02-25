package business.model;

import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * GroupEU represent a set of one or several EUs
 * It contains list of students following these EUs and a list of students for TP and TD.
 * @author DUBUIS Michael
 *
 */
@Entity
public class GroupEU {
	@Id
	private Long id;
	@Column
	private Boolean optionnal;
	@ManyToMany
	@CollectionTable(
			name = "GroupEUListEU",
			joinColumns = @JoinColumn(name = "GroupEUId"))
	private List<EU> eus;
	@ManyToMany
	@CollectionTable(
			joinColumns = @JoinColumn(name="GroupEUId"))
	@MapKeyColumn(name = "GroupNumber")
	@Column(name = "GroupTD")
	private Map<Integer, GroupStudent> td;
	@ManyToMany
	@CollectionTable(
			joinColumns = @JoinColumn(name="GroupEUId"))
	@MapKeyColumn(name = "GroupNumber")
	@Column(name = "GroupTP")
	private Map<Integer, GroupStudent> tp;
	@Column
	private GroupStudent cm;
	
	/**
	 * Empty constructor
	 */
	public GroupEU() {}
	
	/**
	 * Constructor with parameter id
	 * @param id
	 */
	public GroupEU(Long id) {
		this.id = id;
	}
	
	public GroupEU(Long id, Boolean optionnal) {
		this.id = id;
		this.optionnal = (optionnal == null)? false : optionnal;
	}
	
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
	 * @return the optionnal
	 */
	public Boolean getOptionnal() {
		return optionnal;
	}

	/**
	 * @param optionnal the optionnal to set
	 */
	public void setOptionnal(Boolean optionnal) {
		this.optionnal = (optionnal == null)? false : optionnal;
	}

	/**
	 * @return the eus
	 */
	public List<EU> getEus() {
		return eus;
	}

	/**
	 * @param eus the eus to set
	 */
	public void setEus(List<EU> eus) {
		this.eus = eus;
	}

	/**
	 * @return the td
	 */
	public Map<Integer, GroupStudent> getTd() {
		return td;
	}

	/**
	 * @param td the td to set
	 */
	public void setTd(Map<Integer, GroupStudent> td) {
		this.td = td;
	}

	/**
	 * @return the tp
	 */
	public Map<Integer, GroupStudent> getTp() {
		return tp;
	}

	/**
	 * @param tp the tp to set
	 */
	public void setTp(Map<Integer, GroupStudent> tp) {
		this.tp = tp;
	}

	/**
	 * @return the cm
	 */
	public GroupStudent getCm() {
		return cm;
	}

	/**
	 * @param cm the cm to set
	 */
	public void setCm(GroupStudent cm) {
		this.cm = cm;
	}
	
	// TODO UTILS TOOLS FOR GROUPEU (ADDERS & REMOVERS)
}
