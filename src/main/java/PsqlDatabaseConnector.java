import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to handle connections to PostgreSQL database servers.
 */
public class PsqlDatabaseConnector implements IDatabaseConnector {

    public PsqlDatabaseConnector(String host, int port, String database, String user, String password) {

    }

    /**
     * Opens the connection to the database.
     * All the parameters have to be set via the constructor.
     */
    public void open() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "123");
        }
        catch (SQLException exception) {

        }

    }

    /**
     * Closes the connection to the database.
     */
    public void close() {

    }

    /**
     * Adds an article object to the database
     *
     * @param article The article to add to the database.
     */
    public void addArticle(Article article) {

    }
}
