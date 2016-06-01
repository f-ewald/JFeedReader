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
    /**
     * Connection handler.
     */
    private Connection connection;

    /**
     * Database server host.
     */
    private String host;

    /**
     * Database server port.
     */
    private int port;

    /**
     * Database.
     */
    private String database;

    /**
     * Username for database server.
     */
    private String user;

    /**
     * Password for database server.
     */
    private String password;

    /**
     * A logger to log events to the console.
     */
    private Logger logger;

    /**
     * Constructor
     * @param host Database host to connect to.
     * @param port Port of the database host.
     * @param database Database to use
     * @param user Username to login.
     * @param password Password to login.
     */
    public PsqlDatabaseConnector(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.logger = Logger.getLogger("psqldatabase");
    }

    /**
     * Opens the connection to the database.
     * All the parameters have to be set via the constructor.
     */
    public void open() throws ConnectionException {
        try {
            String connectionString = String.format("jdbc:postgresql://%1s:%2s/%3s", host, port, database);
            connection = DriverManager.getConnection(connectionString, user, password);
            logger.info("Connected to database server.");
        }
        catch (SQLException exception) {
            throw new ConnectionException("Failed to open connection to database.");
        }
    }

    /**
     * Closes the connection to the database.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection to database server closed.");
            }
            catch (SQLException exception) {
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
                String sql = String.format("INSERT INTO article(headline, author, published_datetime) VALUES(%s1, %2s, %3s)", article.getHeadline(), article.author, formattedPublishedDateTime);
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.execute();
                logger.info(String.format("Article %1s added to database", article.getHeadline()));
            } catch (SQLException e) {
                logger.severe(String.format("Failed to add article %1s", article.getHeadline()));
                e.printStackTrace();
            }
        }
    }
}
