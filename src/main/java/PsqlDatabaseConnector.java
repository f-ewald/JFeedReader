import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Class to handle connections to PostgreSQL database servers.
 */
public class PsqlDatabaseConnector implements IDatabaseConnector {

    private Connection connection;

    /**
     * Constructor
     * @param host Database host to connect to.
     * @param port Port of the database host.
     * @param database Database to use
     * @param user Username to login.
     * @param password Password to login.
     */
    public PsqlDatabaseConnector(String host, int port, String database, String user, String password) {

    }

    /**
     * Opens the connection to the database.
     * All the parameters have to be set via the constructor.
     */
    public void open() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "123");
        }
        catch (SQLException exception) {
            Logger logger = Logger.getLogger("psqldatabase");
            logger.severe("Failed to open connection to database.");
        }
    }

    /**
     * Closes the connection to the database.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException exception) {
                Logger logger = Logger.getLogger("psqldatabase");
                logger.severe("Failed to close connection to database.");
            }
        }
    }

    /**
     * Adds an article object to the database
     *
     * @param article The article to add to the database.
     */
    public void addArticle(Article article) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattedPublishedDateTime = article.publishedDateTime.format(dateTimeFormatter);

        if (connection != null) {
            try {
                String sql = String.format("INSERT INTO articles(headline, author, published_datetime) VALUES(%s1, %2s, %3s)", article.headline, article.author, formattedPublishedDateTime);
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
