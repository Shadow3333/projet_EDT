package business.model.users;

import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;
import javax.validation.constraints.Past;

import business.model.validation.Email;
import util.Hasher;

/**
 * This class describes an user.
 * @author DUBUIS Michael
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "UserType",
		discriminatorType = DiscriminatorType.STRING)
@Table(name="Users")
public abstract class AbstractUser {
	public static enum PhoneType {
		mobile,
		home,
		work
	}
	
	@Id
	@Email
	private String email;
	@Column(nullable = false)
	private String hashPwd;
	@Column(nullable = true)
	private String firstName;
	@Column(nullable = true)
	private String lastName;
	@Column(nullable = true)
	@Past
	private Date birthDate;
	@Column(nullable = true)
	private String webSite;
	@ElementCollection
	@CollectionTable(
			name = "UsersPhones",
			joinColumns = @JoinColumn(name="UserEmail"))
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "PhoneType")
	@Column(
			name = "PhoneNumber",
			nullable = true)
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
	
	/**
	 * set hashPwd with hash of password
	 * @param password
	 */
	public void setPassword(String password) {
		hashPwd = Hasher.SHA256(password);
	}
	
	public boolean equals(Object other) {
         return other instanceof AbstractUser && (getEmail() != null) ? getEmail().equals(((AbstractUser) other).getEmail()) : (other == this);
     }
 
     // This must return the same hashcode for every Foo object with the same key.
     public int hashCode() {
         return getEmail() != null ? this.getClass().hashCode() + getEmail().hashCode() : super.hashCode();
     }
 
     // Override Object#toString() so that it returns a human readable String representation.
     // It is not required by the Converter or so, it just pleases the reading in the logs.
     public String toString() {
         return "Teacher";
     }
}
