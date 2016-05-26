import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public FeedReader(Feed feed) {
        this.feed = feed;

        // Initialize the logger.
        logger = Logger.getLogger("FeedReader");
    }

    private void read() {
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(this.feed.url));
            for (SyndEntry entry : syndFeed.getEntries ()) {
                try {
                    LocalDateTime publishedDateTime = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    Article article = new Article(entry.getTitle(), publishedDateTime, feed.stopWords);
                    article.author = entry.getAuthor();
                    if (entry.getUpdatedDate() != null) {
                        article.updatedDateTime = entry.getUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    }
                    article.url = new URL(entry.getLink());
                    article.content = entry.getDescription().getValue();
                    if (this.feed.lastArticle == null || !this.feed.lastArticle.equals(article)) {
                        this.feed.articles.add(article);
                    }
                }
                catch (NullPointerException exception) {
                    logger.severe("Failed to read article.");
                }
            }
            // Update the lastupdate to now
            this.feed.lastUpdate = LocalDateTime.now();

            // Update the last fetched article, if articles were fetched during the run.
            if (this.feed.articles.size() > 0) {
                this.feed.lastArticle = this.feed.articles.getLast();
            }

            // Save the fetched articles to a mongodb database.
            IDatabaseConnector database = new MongoDatabaseConnector("127.0.0.1", 27017, null, null, "news");
            database.open();
            for (Article article : this.feed.articles) {
                database.addArticle(article);
            }
            database.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
    }

    /**
     * Fetches the feed in a different thread.
     */
    public void run() {
        // Run the fetch
        this.read();
    }
}
