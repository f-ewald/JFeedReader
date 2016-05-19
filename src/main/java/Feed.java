import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Feed structure for Articles.
 */
public class Feed {
    public String name;
    public URL url;
    public LocalDateTime lastUpdate;

    /**
     * The entries of the feed.
     */
    public LinkedList<Article> articles;

    /**
     * The last fetched article
     */
    public Article lastArticle;

    /**
     * Constructor of a feed.
     * @param name The name of the feed for better readability.
     * @param url The url of the atom / rss feed.
     */
    public Feed(String name, URL url) {
        this.name = name;
        this.url = url;

        articles = new LinkedList<Article>();

        Logger logger = Logger.getLogger("Feed");
        String message = String.format("Created feed: %s", this.name);
        logger.info(message);

    }

}
