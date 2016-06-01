import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Feed structure for Articles.
 * @author Friedrich Ewald
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
    public Queue<Article> articles;

    /**
     * The last fetched article
     */
    public Article lastArticle;

    /**
     * Stop words for this feed.
     */
    public List<String> stopWords;

    public Feed(String name, URL url) {
        this.name = name;
        this.url = url;

        articles = new ArrayDeque<Article>();

        Logger logger = Logger.getLogger("Feed");
        String message = String.format("Created feed: %s", this.name);
        logger.info(message);

    }
}
