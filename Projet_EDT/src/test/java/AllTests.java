import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import business.ManagerTest;
import business.dao.JpaDaoTest;
import business.model.SessionFactoryTest;
import business.model.StudentTest;
import util.HasherTest;
import util.HexaTest;

/**
 * @author DUBUIS Michael
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	StudentTest.class,
	JpaDaoTest.class,
	SessionFactoryTest.class,
	ManagerTest.class,
	HasherTest.class,
	HexaTest.class})
public class AllTests {

}
