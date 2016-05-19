import junit.framework.TestCase;

/**
 * Created by fe on 19.05.16.
 */
public class HeadLineFormatterTest extends TestCase {

    public void testHeadline1() {
        HeadlineFormatter headlineFormatter = new HeadlineFormatter();
        String result = headlineFormatter.getCleanString("and or");
        assertEquals("", result);
    }
}
