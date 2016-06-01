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

    /**
     * Basic constructor.
     * @param feed The feed to fetch.
     */
    public FeedTimerTask(Feed feed) {
        this.feed = feed;
        logger = Logger.getLogger("FeedTimerTask");
    }

    /**
     * Constructor which accepts a feed and stop words.
     * @param feed The feed to fetch.
     * @param stopWords The stop words to apply.
     */
    public FeedTimerTask(Feed feed, HashSet<String> stopWords) {
        this(feed);
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

        FeedReader feedReader = new FeedReader(feed);
        Thread feedFetchThread = new Thread(feedReader);
        feedFetchThread.start();
    }
}
