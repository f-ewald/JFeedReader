import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * The main program.
 */
public class Program {

    // TODO:
    /**
     * Write ConfigurationManager
     * Check Database connector + unit tests
     * Implement run loop
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

    public static void main(String[] args) {
        // Initialize the logger
        final Logger log = Logger.getLogger("main");
        log.info("FeedReader started.");

        // Preparing and parsing the command line options
        Options cliOptions = new Options();
        cliOptions.addOption("d", "debug", false, "show debug information on the console");
        cliOptions.addOption("c", "configuration", true, "use the configuration file");
        cliOptions.addOption("s", "stopwords", true, "use a specific stopwords file");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cli = parser.parse(cliOptions, args);
            if (cli.hasOption("debug")) {
                debug = true;
            }

            if (cli.hasOption("configuration")) {
                configuration = cli.getOptionValue("configuration", null);
            }

            if (cli.hasOption("stopwords")) {
                stopwordFilepath = cli.getOptionValue("stopwords", null);
            }
        }
        catch (ParseException exception) {
            log.severe("Invalid run options have been supplied. Aborting program.");
            log.severe(exception.getMessage());
            System.exit(1);
        }

        // If the configuration file has been set, try to parse it


        List<String> stopwordList = null;
        if (stopwordFilepath != null) {
            try {
                stopwordList = Files.readAllLines(Paths.get(stopwordFilepath));
            }
            catch (IOException e) {}
        }
        try {
            final Feed faz = new Feed("FAZ.net", new URL("http://www.faz.net/rss/aktuell/"), stopwordList);
            //FeedReader reader = new FeedReader(faz);
            //Thread thread = new Thread(reader);
            //thread.start();
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    log.info(String.format("Fetching feed of: %1s", faz.name));
                }
            };
            t.scheduleAtFixedRate(tt, 0, 1000);
        }
        catch (Exception e) {}
        //catch (MalformedURLException e) {

        //}
    }
}
