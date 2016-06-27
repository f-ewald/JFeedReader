import junit.framework.TestCase;
import net.fewald.jfeedreader.ConnectionException;
import net.fewald.jfeedreader.IDatabaseConnector;
import net.fewald.jfeedreader.MongoDatabaseConnector;
import org.junit.Assert;

/**
 * Created by fe on 20.05.16.
 */
public class MongoDatabaseTest extends TestCase {
    private IDatabaseConnector db;
    public void setUp() {
        db = new MongoDatabaseConnector("localhost", 27017, null, null, "news");
    }
    public void testCreateObject() {
        Assert.assertNotNull(db);
    }

    public void testIsOpen1() {
        Assert.assertFalse(db.isOpen());
    }

    public void testIsOpen2() throws ConnectionException {
        db.open();
        Assert.assertTrue(db.isOpen());
        db.close();
    }

    public void testX() {
        try {
            db.open();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
        db.close();
        Assert.assertFalse(db.isOpen());
    }
}
