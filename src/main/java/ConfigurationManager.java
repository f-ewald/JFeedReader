import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.logging.Logger;

/**
 * Provides the configuration for the application.
 */
public class ConfigurationManager {
    /**
     * The underlying configuration manager.
     */
    private PropertiesConfiguration propertiesConfiguration;

    /**
     * a logger.
     */
    private Logger logger;

    /**
     * Constructor which takes care of the initialization.
     */
    public ConfigurationManager() {
        logger = Logger.getLogger("configurationmanager");
        try {
            propertiesConfiguration = new PropertiesConfiguration("configuration.properties");
        } catch (ConfigurationException e) {
            logger.severe("Failed to load configuration file. Using default values.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the configuration as PropertiesConfiguration.
     * @return The properties configuration.
     */
    public PropertiesConfiguration getConfiguration() {
        return propertiesConfiguration;
    }

}
