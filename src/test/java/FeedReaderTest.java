import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleTest.class,
        HeadLineFormatterTest.class,
        MongoDatabaseTest.class,
        PsqlDatabaseTest.class,
        ConfigurationManagerTest.class,
        FeedReaderExceptionTest.class,
        ConnectionExceptionTest.class,
        DatabaseTypeEnumTest.class,
        ContentFormatterTest.class,
        FeedTest.class
})

/**
 * This class is just a placeholder for running all the tests.
 */
public class FeedReaderTest {

}
