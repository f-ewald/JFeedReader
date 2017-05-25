import junit.framework.TestCase;
import net.fewald.jfeedreader.Feed;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Unit test for Feed class.
 */
public class FeedTest extends TestCase {
    public void testFeed1() {
        Feed feed = null;
        URL url = null;
        String name = "Example";
        try {
            url = new URL("http://www.example.com/");
            feed = new Feed(name, url);
        }
        catch (MalformedURLException e) {}
        finally {
            assertEquals(name, feed.getName());
            assertEquals(url, feed.getUrl());
        }
    }
}
