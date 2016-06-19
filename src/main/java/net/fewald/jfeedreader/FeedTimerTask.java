package net.fewald.jfeedreader;

import java.util.HashSet;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Friedrich Ewald
 */
public class FeedTimerTask extends TimerTask  {

    /**
     * The feed to load
     */
    private Feed feed;

    private HashSet<String> stopWords;

    private Logger logger;

    private IDatabaseConnector database;

    /**
     * Basic constructor.
     * @param feed The feed to fetch.
     */
    public FeedTimerTask(Feed feed, IDatabaseConnector database) {
        this.feed = feed;
        this.database = database;
        logger = Logger.getLogger("FeedTimerTask");
    }

    /**
     * Constructor which accepts a feed and stop words.
     * @param feed The feed to fetch.
     * @param stopWords The stop words to apply.
     */
    public FeedTimerTask(Feed feed, IDatabaseConnector database, HashSet<String> stopWords) {
        this(feed, database);
        this.stopWords = stopWords;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        logger.info(String.format("Fetching feed %1s", feed.name));

        FeedReader feedReader = new FeedReader(feed, database, stopWords);
        Thread feedFetchThread = new Thread(feedReader);
        feedFetchThread.start();
    }
}
