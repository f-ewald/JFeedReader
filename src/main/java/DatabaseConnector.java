import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.logging.Logger;

/**
 * Created by fe on 13.05.16.
 */
public class DatabaseConnector {

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
    public DatabaseConnector(String host, int port, String username, String password, String database) {
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
            Logger logger = Logger.getLogger("DatabaseConnector");
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

    /**
     * Inserts an entry into a specified table with the given database
     * @param table The table to insert the data.
     * @param document The document containing the data to insert.
     */
    public void insert(String table, Document document) {
        MongoCollection<Document> collection = db.getCollection(table);
        collection.insertOne(document);
    }
}
