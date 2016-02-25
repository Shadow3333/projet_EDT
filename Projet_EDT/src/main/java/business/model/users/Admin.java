package business.model.users;

import javax.persistence.Entity;

/**
 * Admin extends AbsctractUser. This class is used to describe an user with administrator's rights.
 * @author DUBUIS Michael
 *
 */
@Entity
public class Admin extends Teacher {
	
	/**
	 * Empty Constructor
	 */
	public Admin() {
		super();
	}

}
