import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Friedrich Ewald
 */
public class FeedTimerTask extends TimerTask  {

    private Feed feed;

    private Logger logger;

    public FeedTimerTask(Feed feed) {
        this.feed = feed;
        logger = Logger.getLogger("FeedTimerTask");
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
