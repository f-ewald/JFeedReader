import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.logging.Logger;

/**
 * Database connector for MongoDB
 */
public class MongoDatabaseConnector implements IDatabaseConnector {

    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    /**
     * The database handler
     */
    private MongoDatabase db;

    /**
     * The client to access the database
     */
    private MongoClient client;

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

        CodecRegistry registry = CodecRegistries.fromCodecs(new UrlCodec());
    }

    /**
     * Open a connection to the database server.
     */
    public void open() {
        try {
            client = new MongoClient(host, port);
            db = client.getDatabase(database);
        }
        catch (MongoSocketOpenException exception) {
            Logger logger = Logger.getLogger("MongoDatabaseConnector");
            logger.severe("Cannot open connection to database");
        }
    }

    /**
     * Closes the connection to the database server.
     */
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public void addArticle(Article article) {
        MongoCollection<Document> collection = db.getCollection("article");
        collection.insertOne(article.getDocument());
    }
}
