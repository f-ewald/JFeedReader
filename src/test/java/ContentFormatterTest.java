import junit.framework.TestCase;
import net.fewald.jfeedreader.ContentFormatter;

/**
 * Created by fe on 30.06.16.
 */
public class ContentFormatterTest extends TestCase {
    public void testCleanHtml() {
        String html = "<body>Example</body>";
        String clean = ContentFormatter.cleanHtml(html);
        String expected = "Example";
        assertEquals(expected, clean);
    }
}
