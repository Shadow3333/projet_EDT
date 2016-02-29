import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import business.ManagerTest;
import business.dao.JpaDaoTest;
import business.model.SessionFactoryTest;
import business.model.StudentTest;

/**
 * @author DUBUIS Michael
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	StudentTest.class,
	JpaDaoTest.class,
	SessionFactoryTest.class,
	ManagerTest.class})
public class AllTests {

}
