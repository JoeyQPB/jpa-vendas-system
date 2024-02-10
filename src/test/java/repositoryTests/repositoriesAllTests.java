package repositoryTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClientRepositoryTest.class, ProductRepositoryTest.class,
		SaleRepositoryTest.class })
public class repositoriesAllTests {

}
