import junit.framework.TestCase;

/**
 * Created by fe on 19.05.16.
 */
public class HeadLineFormatterTest extends TestCase {
    private HeadlineFormatter headlineFormatter;

    public void setUp() {
        headlineFormatter = new HeadlineFormatter();
    }

    public void testHeadline1() {
        String result = headlineFormatter.getCleanString("and or");
        assertEquals("", result);
    }

    public void testHeadline2() {
        String result = headlineFormatter.getCleanString("aNd:oR");
        assertEquals("", result);
    }

    public void testHeadlineEmpty() {
        String result = headlineFormatter.getCleanString("");
        assertEquals("", result);
    }

    public void testHeadline3() {
        String result = headlineFormatter.getCleanString("and MyTestString or");
        assertEquals(result, "myteststring");
    }

    public void testHeadline4() {
        String result = headlineFormatter.getCleanString("and MyTestString number2 or");
        assertEquals(result, "myteststring number2");
    }
}
