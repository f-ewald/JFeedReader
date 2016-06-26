import junit.framework.TestCase;
import net.fewald.jfeedreader.FeedReadException;
import org.junit.Assert;

/**
 * Created by fe on 26.06.16.
 */
public class FeedReaderExceptionTest extends TestCase {
    public void testCreateException() {
        FeedReadException ex = new FeedReadException();
        Assert.assertNotNull(ex);
    }

    public void testExceptionMessage() {
        String msg = "Exception Message";
        FeedReadException ex = new FeedReadException(msg);
        Assert.assertEquals(msg, ex.getMessage());
    }
}
