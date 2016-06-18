package net.fewald.jfeedreader;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;

/**
 * The main program.
 * @author Friedrich Ewald
 */
public class Program {

    // TODO:
    /**
     * Create program to resume and not double download articles
     *
     * Write default configuration
     * Check Database connector + unit tests
     * Organize deploy
     * Improve class for Psql connector
     */

    /**
     * Flag, if the program is in debug mode.
     */
    private static boolean debug;

    /**
     * File path to the configuration file.
     * null, if not set.
     */
    private static String configuration;

    /**
     * File path to the stop words file.
     * null, if not set.
     */
    private static String stopwordFilepath;

    /**
     * The list of feeds to fetch.
     */
    private static List<Feed> feedList;

    /**
     * The type of the database to use.
     */
    private static DatabaseTypeEnum databaseType;

    public static void main(String[] args) {
        Program p = new Program();
        String t = p.getClass().getCanonicalName();
        // Initialize the logger
        final Logger log = Logger.getLogger("main");
        log.info("FeedReader started.");

        // Preparing and parsing the command line options
        Options cliOptions = new Options();
        cliOptions.addOption("f", "feed", true, "the feed file to load");
        cliOptions.addOption("d", "debug", false, "show debug information on the console");
        cliOptions.addOption("c", "configuration", true, "use the configuration file");
        cliOptions.addOption("s", "stopwords", true, "use a specific stopwords file");
        cliOptions.addOption("db", "database", true, "Which database type to use (postgres|mongodb)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cli = parser.parse(cliOptions, args);

            if (cli.hasOption("feed")) {
                feedList = new LinkedList<Feed>();
                FeedFileHandler feedFileHandler = new FeedFileHandler();
                try {
                    feedList = feedFileHandler.getFeedList(cli.getOptionValue("feed", null));
                }
                catch (IOException exception) {
                    log.severe("Could not load feed file");
                    System.exit(1);
                }
            }
            else {
                log.severe("No feed file defined. Aborting.");
                System.exit(1);
            }

            if (cli.hasOption("debug")) {
                debug = true;
            }

            if (cli.hasOption("configuration")) {
                configuration = cli.getOptionValue("configuration", null);
                if (configuration != null) {
                    // TODO: Finish read of configuration file.
                }
            }

            if (cli.hasOption("stopwords")) {
                stopwordFilepath = cli.getOptionValue("stopwords", null);
            }

            if (cli.hasOption("database")) {
                String dbType = cli.getOptionValue("database", "mongodb");
                DatabaseTypeEnum databaseType = DatabaseTypeEnum.fromString(dbType);
            }
        }
        catch (ParseException exception) {
            log.severe("Invalid run options have been supplied. Aborting program.");
            log.severe(exception.getMessage());
            System.exit(1);
        }

        HashSet<String> stopWords = null;
        if (stopwordFilepath == null) {
            try {
                stopWords = ResourceHandler.getResourceAsHashSet("stopwords_en.txt");
            }
            catch (IOException exception) {
                log.severe("Could not load stopwords file.");
            }
        }
        for (Feed feed : feedList) {
            Timer timer = new Timer();
            FeedTimerTask timerTask = new FeedTimerTask(feed, stopWords);
            // Run the task every five minutes.
            timer.scheduleAtFixedRate(timerTask, 0, 1000 * 20);
        }
    }
}
