import org.apache.commons.cli.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by fe on 04.05.16.
 */
public class Program {

    /**
     * Flag, if the program is in debug mode.
     */
    private static boolean debug;

    /**
     * Filepath to the configuration file.
     * Null, if not set.
     */
    private static String configuration;

    public static void main(String[] args) {
        // Initialize the logger
        Logger log = Logger.getLogger("main");
        log.info("FeedReader started.");

        // Preparing and parsing the command line options
        Options cliOptions = new Options();
        cliOptions.addOption("d", "debug", false, "show debug information on the console");
        cliOptions.addOption("c", "configuration", true, "use the configuration file");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cli = parser.parse(cliOptions, args);
            if (cli.hasOption("debug")) {
                debug = true;
            }

            if (cli.hasOption("configuration")) {
                configuration = cli.getOptionValue("configuration", null);
            }
        }
        catch (ParseException exception) {
            log.severe("Invalid run options have been supplied. Aborting program.");
            log.severe(exception.getMessage());
            System.exit(1);
        }

        // If the configuration file has been set, try to parse it

        try {
            Feed faz = new Feed("FAZ.net", new URL("http://www.faz.net/rss/aktuell/"));
            FeedReader reader = new FeedReader(faz);
            Thread thread = new Thread(reader);
            thread.start();
        }
        catch (MalformedURLException e) {

        }



    }
}
