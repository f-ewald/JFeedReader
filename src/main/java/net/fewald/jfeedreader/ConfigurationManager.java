package net.fewald.jfeedreader;

import java.util.logging.Logger;

/**
 * Provides the configuration for the application.
 */
public class ConfigurationManager {
    /**
     * The underlying configuration manager.
     */
    //private PropertiesConfiguration propertiesConfiguration;

    /**
     * a logger.
     */
    private Logger logger;

    /**
     * Constructor which takes care of the initialization.
     */
    public ConfigurationManager() {
        logger = Logger.getLogger("configurationmanager");

    }

    /**
     * Returns the configuration as PropertiesConfiguration.
     * @return The properties configuration.
     */

}
