package net.fewald.jfeedreader;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Database connector for MongoDB
 */
public class MongoDatabaseConnector implements IDatabaseConnector {
    /**
     * Host of the MongoDB
     */
    private String host;
    /**
     * Port of the MongoDB
     */
    private int port;
    /**
     * Database of the MongoDB
     */
    private String database;
    /**
     * Username of the MongoDB. Not used if empty or null.
     */
    private String username;
    /**
     * Password of the MongoDB. Not used if empty or null.
     */
    private String password;

    /**
     * The database handler
     */
    private MongoDatabase db;

    /**
     * The client to access the database
     */
    private MongoClient client;

    /**
     * Indicator, if the connection is currently open.
     */
    private boolean isOpen;

    /**
     * Number of connections to the database.
     *
     */
    private int connectionCount;

    /**
     * Logging for the console.
     */
    private Logger logger;

    /**
     * Constructor with all the required arguments.
     * It does not connect to the database but prepares it.
     * To connect to the database call open().
     * @param host The host to connect to.
     * @param port The port the host listens on.
     * @param username The username to use.
     * @param password The password (plaintext) to use.
     * @param database The database to connect to.
     */
    public MongoDatabaseConnector(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

        this.logger = Logger.getLogger("mongodatabase");
    }

    public synchronized boolean isOpen() {
        return isOpen;
    }

    /**
     * Open a connection to the database server.
     */
    public synchronized void open() throws ConnectionException {
        if (connectionCount++ == 0) {
            try {
                ServerAddress serverAddress = new ServerAddress(host, port);

                if (username == null || username.length() == 0) {
                    client = new MongoClient(serverAddress);
                } else {
                    MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
                    List<MongoCredential> credentialList = new LinkedList<MongoCredential>();
                    credentialList.add(credential);
                    client = new MongoClient(serverAddress, credentialList);
                }
                db = client.getDatabase(database);
                isOpen = true;
                logger.info("Connected to MongoDB.");
            } catch (MongoSocketOpenException exception) {
                throw new ConnectionException("Could not connect to MongoDB.");
            }
        }
    }

    /**
     * Closes the connection to the database server.
     */
    public synchronized void close() {
        if (client != null && --connectionCount == 0) {
            client.close();
            isOpen = false;
            logger.info("Connection to MongoDB closed.");
        }
    }

    /**
     * Adds an article to the database.
     * @param article The article to add to the database.
     */
    public synchronized void addArticle(Article article) {
        MongoCollection<Document> collection = db.getCollection("article");
        try {
            collection.insertOne(article.getDocument());
        }
        catch (IllegalStateException exception) {
            System.out.println();
        }
    }
}
