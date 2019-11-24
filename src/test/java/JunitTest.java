import com.rest.server.AccountTest;
import com.rest.server.UserAccountsTest;
import com.rest.server.UserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserTest.class,
        AccountTest.class,
        UserAccountsTest.class
})
public class JunitTest {
}
