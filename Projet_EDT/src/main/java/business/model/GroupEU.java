package business.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.validation.Valid;

import business.model.EU.LessonType;
import business.model.users.AbstractUser;

/**
 * GroupEU represent a set of one or several EUs
 * It contains list of students following these EUs and a list of students for TP and TD.
 * @author DUBUIS Michael
 *
 */
@Entity
public class GroupEU {
	private static final Integer capacityTD = 30;
	private static final Integer capacityTP = 20;
	
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = true)
	private Boolean optionnal;
	@ManyToMany(cascade=CascadeType.PERSIST)
	@CollectionTable(
			name = "GroupEUListEU",
			joinColumns = @JoinColumn(name = "GroupEUId"))
	@Valid
	private List<EU> eus;
	@ManyToMany(cascade=CascadeType.ALL)
	@CollectionTable(
			joinColumns = @JoinColumn(name="GroupEUId"))
	@MapKeyColumn(name = "GroupNumber")
	@Valid
	private Map<Integer, GroupStudent> td;
	@ManyToMany(cascade=CascadeType.ALL)
	@CollectionTable(
			joinColumns = @JoinColumn(name="GroupEUId"))
	@MapKeyColumn(name = "GroupNumber")
	@Valid
	private Map<Integer, GroupStudent> tp;
	@ManyToOne(cascade=CascadeType.ALL)
	@Valid
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
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof GroupEU)) {
			return false;
		}
		GroupEU g = (GroupEU) obj;
		return g.getId().equals(this.getId());
	}
	
	/**
	 * Add an eu to eus.
	 * If eus is null, will initialized it.
	 * @param eu
	 * @return true if added, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean addEU(EU eu) throws IllegalArgumentException {
		if(eu == null) {
			throw new IllegalArgumentException(
					"eu must be different than Null !");
		}
		if(eus == null) {
			eus = new ArrayList<EU>();
		}
		return eus.add(eu);
	}
	
	/**
	 * Remove a eu to eus.
	 * @param eu
	 * @return true if removed, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean removeEU(EU eu) throws IllegalArgumentException {
		if(eu == null) {
			throw new IllegalArgumentException(
					"eu must be different than Null !");
		}
		return eus.remove(eu);
	}
	
	/**
	 * Add an user to cm.
	 * If cm is null, will initialized if with a new GroupStudent.
	 * @param student
	 * @return true if added, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean addUserToCM(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(cm != null
				&& cm.getStudents() != null
				&& cm.getStudents().contains(student)) {
			return false;
		}
		if(cm == null) {
			cm = new GroupStudent();
			cm.setGroupType(LessonType.CM);
		}
		return cm.addStudent(student);
	}
	
	/**
	 * Remove an user to cm. 
	 * @param student
	 * @return true if added, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean removeUserToCM(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(cm != null
				&& cm.getStudents() != null
				&& cm.getStudents().contains(student)) {
			return cm.removeStudent(student);
		}
		System.err.println("student is not in group of CM");
		return false;
	}
	
	/**
	 * Add an user to a group of td
	 * If td is null will initialized it.
	 * If td at num is null, will initialized if with a new GroupStudent.
	 * @param num - number of group where add user
	 * @param student
	 * @return true if added, else false
	 * @throws IllegalArgumentException - if num or eu is null
	 */
	public boolean addUserToTD(Integer num, AbstractUser student) throws IllegalArgumentException {
		if(num == null) {
			throw new IllegalArgumentException(
					"num must be different than Null");
		}
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(td == null) {
			td = new HashMap<Integer, GroupStudent>();
		}
		for(Entry<Integer, GroupStudent> e : td.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				System.err.println("Student already in TD group n°" + e.getKey());
				return false;
			}
		}
		if(!td.containsKey(num)) {
			GroupStudent groupStudent = new GroupStudent();
			groupStudent.setGroupType(LessonType.TP);
			return groupStudent.addStudent(student);
		}
		return td.get(num).addStudent(student);
	}
	
	/**
	 * Add an user to the first not full group of td
	 * @param student
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean addUserToTD(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(td == null) {
			td = new HashMap<Integer, GroupStudent>();
		}
		Integer firstGroupNotFull = 0;
		Integer lastGroupFull = 0;
		for(Entry<Integer, GroupStudent> e : td.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				System.err.println("Student already in TD group n°" + e.getKey());
				return false;
			}
			if(firstGroupNotFull <= 0
					&& e.getValue() != null
					&& e.getValue().getStudents().size() < capacityTD) {
				firstGroupNotFull = e.getKey();
			}
			lastGroupFull = Integer.max(lastGroupFull, e.getKey());
		}
		if(firstGroupNotFull == 0) {
			GroupStudent newGroupStudent = new GroupStudent();
			newGroupStudent.setGroupType(LessonType.TD);
			td.put(lastGroupFull + 1, newGroupStudent);
			firstGroupNotFull = lastGroupFull + 1;
		}
		return addUserToTD(firstGroupNotFull, student);
	}
	
	/**
	 * Remove an user to group of td.
	 * @param student
	 * @return true if removed, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean removeUserToTD(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(td == null) {
			td = new HashMap<Integer, GroupStudent>();
		}
		for(Entry<Integer, GroupStudent> e : td.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				return e.getValue().removeStudent(student);
			}
		}
		System.err.println("student is not in any group of TD.");
		return false;
	}

	/**
	 * Add an user to a group of tp.
	 * If tp is null will initialized it.
	 * If tp at num is null, will initialized if with a new GroupStudent.
	 * @param num - number of group where add user
	 * @param student
	 * @return true if added, else false
	 * @throws IllegalArgumentException - if num or eu is null
	 */
	public boolean addUserToTP(Integer num, AbstractUser student) throws IllegalArgumentException {
		if(num == null) {
			throw new IllegalArgumentException(
					"num must be different than Null");
		}
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(tp == null) {
			tp = new HashMap<Integer, GroupStudent>();
		}
		for(Entry<Integer, GroupStudent> e : tp.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				System.err.println("Student already in TP group n°" + e.getKey());
				return false;
			}
		}
		if(!tp.containsKey(num)) {
			GroupStudent groupStudent = new GroupStudent();
			groupStudent.setGroupType(LessonType.TP);
			return groupStudent.addStudent(student);
		}
		return tp.get(num).addStudent(student);
	}
	
	/**
	 * Add an user to the first not full group of tp
	 * @param student
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean addUserToTP(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(tp == null) {
			tp = new HashMap<Integer, GroupStudent>();
		}
		Integer firstGroupNotFull = 0;
		Integer lastGroupFull = 0;
		for(Entry<Integer, GroupStudent> e : tp.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				System.err.println("Student already in TP group n°" + e.getKey());
				return false;
			}
			if(firstGroupNotFull <= 0
					&& e.getValue() != null
					&& e.getValue().getStudents().size() < capacityTP) {
				firstGroupNotFull = e.getKey();
			}
			lastGroupFull = Integer.max(lastGroupFull, e.getKey());
		}
		if(firstGroupNotFull == 0) {
			GroupStudent newGroupStudent = new GroupStudent();
			newGroupStudent.setGroupType(LessonType.TP);
			tp.put(lastGroupFull + 1, newGroupStudent);
			firstGroupNotFull = lastGroupFull + 1;
		}
		return addUserToTP(firstGroupNotFull, student);
	}

	/**
	 * Remove an user to group of tp.
	 * @param student
	 * @return true if removed, else false
	 * @throws IllegalArgumentException - if eu is null
	 */
	public boolean removeUserToTP(AbstractUser student) throws IllegalArgumentException {
		if(student == null) {
			throw new IllegalArgumentException(
					"student must be different than Null !");
		}
		if(tp == null) {
			tp = new HashMap<Integer, GroupStudent>();
		}
		for(Entry<Integer, GroupStudent> e : tp.entrySet()) {
			if(e.getValue() != null
					&& e.getValue().getStudents() != null
					&& e.getValue().getStudents().contains(student)) {
				return e.getValue().removeStudent(student);
			}
		}
		System.err.println("student is not in any group of TP.");
		return false;
	}
	
	/**
	 * Add an user to cm, group of td and group of tp.
	 * If numTD or/and numTP is null, will ignore this/these adding and return true.
	 * @see addUserToCM
	 * @see addUSerToTD
	 * @see addUserToTP
	 * @param numTD - number of group of td where add user
	 * @param numTP - number of group of tp where add user
	 * @param student
	 * @return {added to cm, added to td, added to tp}
	 * @throws IllegalArgumentException - if student is null
	 */
	public boolean[] addUser(Integer numTD, Integer numTP, AbstractUser student)
			throws IllegalArgumentException {
		boolean added[] = new boolean[3];
		added[0] = addUserToCM(student);
		added[1] = (numTD != null) ? addUserToTD(numTD, student) : true;
		added[2] = (numTP != null) ? addUserToTP(numTP, student) : true;
		return added;
	}
	
	/**
	 * Remove an user to cm, group of td and group of tp.
	 * @see removeUserToCM
	 * @see removeUSerToTD
	 * @see removeUserToTP
	 * @param student
	 * @return {removed to cm, removed to td, removed to tp}
	 */
	public boolean[] removeUser(AbstractUser student)
			throws IllegalArgumentException {
		boolean removed[] = new boolean[3];
		removed[0] = removeUserToCM(student);
		removed[1] = removeUserToTD(student);
		removed[2] = removeUserToTP(student);
		return removed;
	}
}
