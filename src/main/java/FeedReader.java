import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.*;

/**
 * Class to perform asynchronous requests and read the RSS/Atom feed
 * One instance per feed.
 */
public class FeedReader implements Runnable {
    /**
     * The feed which is processed by this FeedReader instance.
     */
    private Feed feed;

    public FeedReader(Feed feed) {
        this.feed = feed;
    }

    private void read() {
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(this.feed.url));
            for (SyndEntry entry : feed.getEntries ()) {
                Article article = new Article();
                article.author = entry.getAuthor();

                article.publishedDateTime = entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                article.updatedDateTime = entry.getUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                article.headline = entry.getTitle();
                article.url = new URL(entry.getLink());
                article.content = entry.getDescription().getValue();
                if (this.feed.lastArticle == null || !this.feed.lastArticle.equals(article)) {
                    this.feed.articles.add(article);
                }
            }
            // Update the lastupdate to now
            this.feed.lastUpdate = LocalDateTime.now();

            // Update the last fetched article, if articles were fetched during the run.
            if (this.feed.articles.size() > 0) {
                this.feed.lastArticle = this.feed.articles.getLast();
            }

            // Save the fetched articles to a mongodb database.
            DatabaseConnector database = new DatabaseConnector("127.0.0.1", 27017, null, null, "news");
            database.open();
            for (Article article : this.feed.articles) {
                database.insert("table", article.getDocument());
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
