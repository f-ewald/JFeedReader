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
     * The current articles.
     * Used so that there is no article is fetched twice.
     */
    public HashSet<String> currentArticles;

    /**
     * Stop words for this feed.
     */
    public List<String> stopWords;

    public Feed(String name, URL url) {
        this.name = name;
        this.url = url;

        currentArticles = new HashSet<String>();

        Logger logger = Logger.getLogger(this.getClass().getName());
        String message = String.format("Created feed: %s", this.name);
        logger.info(message);

    }
}
