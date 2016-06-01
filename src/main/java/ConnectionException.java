/**
 * Connection exception which occurs when there is no connection to the database.
 */
public class ConnectionException extends Exception {
    /**
     * Default constructor
     */
    public ConnectionException() {
        super();
    }

    /**
     * Constructor for ConnectionException with message.
     * @param message The message
     */
    public ConnectionException(String message) {
        super(message);
    }
}
