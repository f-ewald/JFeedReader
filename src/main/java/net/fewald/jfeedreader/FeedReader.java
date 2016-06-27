package net.fewald.jfeedreader;

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
import java.util.concurrent.Semaphore;
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

    /**
     * The connection to the database.
     */
    public IDatabaseConnector database;

    private Semaphore semaphore;

    private HashSet<String> stopWords;

    public FeedReader(Feed feed, IDatabaseConnector database) {
        this.feed = feed;
        this.database = database;

        // Initialize the logger.
        logger = Logger.getLogger("FeedReader");
    }

    public FeedReader(Semaphore semaphore, Feed feed, IDatabaseConnector database, HashSet<String> stopWords) {
        this(feed, database);
        this.semaphore = semaphore;
        this.stopWords = stopWords;
    }

    private void read() throws FeedReadException, ConnectionException {
        HashSet<String> articleHeadlinesCurrentRunHashSet = new HashSet<String>();
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
        HeadlineFormatter headlineFormatter = new HeadlineFormatter(this.stopWords);

        for (SyndEntry entry : syndFeed.getEntries ()) {
            // Convert the Date object to LocalDateTimeObject
            LocalDateTime publishedDateTime = null;
            if (entry.getPublishedDate() != null) {
                 publishedDateTime = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            else {
                logger.severe("published data is missing, cannot add this entry to the list.");
                continue;
            }

            // Create a new article object and set the values
            Article article = new Article(entry.getTitle(), publishedDateTime);

            // Copy over the feed name for later evaluation.
            article.feedName = feed.name;

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
                String link = entry.getLink();
                if (link != null && link.length() > 0 && link.startsWith("http:")) {
                    url = new URL(entry.getLink());
                }
            }
            catch (MalformedURLException exception) {
                logger.info("Could not format URL for article " + article.getHeadlineOriginal());
            }
            article.url = url;
            article.content = ContentFormatter.cleanHtml(entry.getDescription().getValue());

            // Check, if the article is in the current list of articles already.
            // If this is the case, don't add it to the database because this would
            // be a duplicate.
            if (!feed.currentArticles.contains(article.getHeadline())) {
                // Add the article to the queue, if it is not the latest one.
                feed.articles.add(article);
            }
            // Add the headline to the current processed headlines.
            articleHeadlinesCurrentRunHashSet.add(article.getHeadline());
        }

        // Update the last update to now
        feed.lastUpdate = LocalDateTime.now();

        // Remove all but the last element from the queue.
        while (feed.articles.size() > 0) {
            Article a = feed.articles.remove();
            database.addArticle(a);
        }

        // Clean up the current articles
        feed.currentArticles = articleHeadlinesCurrentRunHashSet;
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
        finally {
            // Release the semaphore
            semaphore.release();
        }
    }
}
