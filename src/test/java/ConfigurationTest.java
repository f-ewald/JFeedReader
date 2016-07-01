import junit.framework.TestCase;
import net.fewald.jfeedreader.Configuration;

/**
 * Created by fe on 30.06.16.
 */
public class ConfigurationTest extends TestCase {
    public void testConstructor() {
        Configuration configuration = new Configuration();
        assertNotNull(configuration);
    }
}
