package net.fewald.jfeedreader;

import java.util.HashSet;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

/**
 * @author Friedrich Ewald
 */
public class FeedTimerTask extends TimerTask  {

    /**
     * The feed to load
     */
    private List<Feed> feedList;

    private HashSet<String> stopWords;

    private Logger logger;

    private IDatabaseConnector database;

    private Semaphore semaphore;

    /**
     * Basic constructor.
     * @param feedList The feeds to fetch.
     */
    public FeedTimerTask(List<Feed> feedList, IDatabaseConnector database) {
        this.feedList = feedList;
        this.database = database;
        logger = Logger.getLogger("FeedTimerTask");
        semaphore = new Semaphore(10);
    }

    /**
     * Constructor which accepts a feed and stop words.
     * @param feedList The feed to fetch.
     * @param stopWords The stop words to apply.
     */
    public FeedTimerTask(List<Feed> feedList, IDatabaseConnector database, HashSet<String> stopWords) {
        this(feedList, database);
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
        for (Feed feed : feedList) {
            try {
                semaphore.acquire();
                logger.info(String.format("Fetching feed %1s", feed.name));
                FeedReader feedReader = new FeedReader(semaphore, feed, database, stopWords);
                Thread feedFetchThread = new Thread(feedReader);
                feedFetchThread.start();
            }
            catch (InterruptedException exception) {

            }
        }
    }
}
