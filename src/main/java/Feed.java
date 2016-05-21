import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Feed structure for Articles.
 */
public class Feed {
    /**
     * The name of the feed in human readable format.
     */
    public String name;

    /**
     * The URL of the feed.
     */
    public URL url;

    /**
     * Last update of the feed.
     */
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
     * Stop words for this feed.
     */
    public List<String> stopWords;

    /**
     * Constructor of a feed.
     * @param name The name of the feed for better readability.
     * @param url The url of the atom / rss feed.
     */
    public Feed(String name, URL url, List<String> stopWords) {
        this.name = name;
        this.url = url;
        this.stopWords = stopWords;

        articles = new LinkedList<Article>();

        Logger logger = Logger.getLogger("Feed");
        String message = String.format("Created feed: %s", this.name);
        logger.info(message);

    }

}
