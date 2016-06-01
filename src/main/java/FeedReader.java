import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Class to perform asynchronous requests and read the RSS/Atom feed
 * One instance per feed.
 */
public class FeedReader implements Runnable {
    /**
     * The feed which is processed by this FeedReader instance.
     */
    private Feed feed;

    /**
     * Logger
     */
    private Logger logger;

    private HashSet<String> stopWords;

    public FeedReader(Feed feed) {
        this.feed = feed;

        // Initialize the logger.
        logger = Logger.getLogger("FeedReader");
    }

    private void read() throws FeedReadException, ConnectionException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed syndFeed = null;
        try {
            syndFeed = input.build(new XmlReader(this.feed.url));
        }
        catch (IOException exception) {
            throw new FeedReadException("Error while loading feed.");
        }
        catch (FeedException exception) {
            throw new FeedReadException("Error while loading feed.");
        }

        // Initialize headline formatter with stop words
        HeadlineFormatter headlineFormatter = new HeadlineFormatter(this.feed.stopWords);

        for (SyndEntry entry : syndFeed.getEntries ()) {
            // Convert the Date object to LocalDateTimeObject
            LocalDateTime publishedDateTime = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // Create a new article object and set the values
            Article article = new Article(entry.getTitle(), publishedDateTime);
            if (article.getHeadlineOriginal() != null) {
                String cleanedHeadline = headlineFormatter.getCleanString(article.getHeadlineOriginal());
                article.setHeadline(cleanedHeadline);
            }
            article.author = entry.getAuthor();
            if (entry.getUpdatedDate() != null) {
                article.updatedDateTime = entry.getUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }

            // Try to build the url and write null otherwise after informing the user.
            URL url = null;
            try {
                url = new URL(entry.getLink());
            }
            catch (MalformedURLException exception) {
                logger.info("Could not format URL for article " + article.getHeadlineOriginal());
            }
            article.url = url;
            article.content = ContentFormatter.cleanHtml(entry.getDescription().getValue());
            if (this.feed.lastArticle == null || !this.feed.lastArticle.equals(article)) {
                this.feed.articles.add(article);
            }
        }
        // Update the last update to now
        this.feed.lastUpdate = LocalDateTime.now();

        IDatabaseConnector database = new MongoDatabaseConnector("127.0.0.1", 27017, null, null, "news");
        database.open();

        // Remove all but the last element from the queue.
        while (feed.articles.size() > 1) {
            Article a = feed.articles.remove();
            database.addArticle(a);
        }
        database.close();
    }

    /**
     * Fetches the feed in a different thread.
     */
    public void run() {
        // Run the fetch
        try {
            this.read();
        } catch (FeedReadException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (ConnectionException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
