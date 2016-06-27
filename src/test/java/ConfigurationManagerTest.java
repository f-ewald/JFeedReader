import junit.framework.TestCase;
import net.fewald.jfeedreader.ConfigurationManager;
import org.junit.Assert;

/**
 * Created by fe on 20.05.16.
 */
public class ConfigurationManagerTest extends TestCase {
    public void testConfigurationManager() {
        ConfigurationManager cm = new ConfigurationManager();
        Assert.assertNotNull(cm);
    }
}
