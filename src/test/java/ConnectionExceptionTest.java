import junit.framework.TestCase;
import net.fewald.jfeedreader.ConnectionException;
import org.junit.Assert;

/**
 * Created by fe on 27.06.16.
 */
public class ConnectionExceptionTest extends TestCase {
    public void testCreateException() {
        ConnectionException ex = new ConnectionException();
        Assert.assertNotNull(ex);
    }

    public void testExceptionMessage() {
        String msg = "Exception Message";
        ConnectionException ex = new ConnectionException(msg);
        Assert.assertEquals(msg, ex.getMessage());
    }
}
