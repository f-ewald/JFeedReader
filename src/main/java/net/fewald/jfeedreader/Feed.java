package net.fewald.jfeedreader;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Feed structure for Articles.
 * @author Friedrich Ewald
 */
public class Feed {
    /**
     * The name of the feed in human readable format.
     */
    private String name;

    /**
     * The URL of the feed.
     */
    private URL url;

    /**
     * Last update of the feed.
     */
    private LocalDateTime lastUpdate;

    /**
     * The current articles.
     * Used so that there is no article is fetched twice.
     */
    private HashSet<String> currentArticles;

    /**
     * Stop words for this feed.
     */
    private List<String> stopWords;

    private static Logger logger = Logger.getLogger(Feed.class.getName());

    public Feed(String name, URL url) {
        this.name = name;
        this.url = url;

        currentArticles = new HashSet<String>();

        String message = String.format("Created feed: %s", this.name);
        logger.info(message);
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getCurrentArticles() {
        return currentArticles;
    }

    public void setCurrentArticles(HashSet<String> currentArticles) {
        this.currentArticles = currentArticles;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
