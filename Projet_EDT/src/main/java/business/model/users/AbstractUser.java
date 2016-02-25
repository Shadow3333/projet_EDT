package business.model.users;

import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class describes an user.
 * @author DUBUIS Michael
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "UserType",
		discriminatorType = DiscriminatorType.STRING)
@Table(name="Users")
@NamedQueries({
	@NamedQuery(
			name="findAll",
			query="SELECT u FROM AbstractUser u")
})
public abstract class AbstractUser {
	public static enum PhoneType {
		mobile,
		home,
		work
	}
	
	@Id
	private String email;
	@Column
	private String hashPwd;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Date birthDate;
	@Column
	private String webSite;
	@ElementCollection
	@CollectionTable(
			name = "UsersPhones",
			joinColumns = @JoinColumn(name="UserEmail"))
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "PhoneType")
	@Column(name = "PhoneNumber")
	private Map<PhoneType, String> phones;
	
	/**
	 * Empty Constructor
	 */
	public AbstractUser() {
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the hashPwd
	 */
	public String getHashPwd() {
		return hashPwd;
	}

	/**
	 * @param hashPwd the hashPwd to set
	 */
	public void setHashPwd(String hashPwd) {
		this.hashPwd = hashPwd;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the webSite
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * @param webSite the webSite to set
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * @return the phones
	 */
	public Map<PhoneType, String> getPhones() {
		return phones;
	}

	/**
	 * @param phones the phones to set
	 */
	public void setPhones(Map<PhoneType, String> phones) {
		this.phones = phones;
	}
}
