package net.fewald.jfeedreader;

/**
 * Interface to handle the database connection
 */
public interface IDatabaseConnector {
    /**
     * Returns true, if the connection is currently open and false otherwise.
     * @return true if open, false otherwise.
     */
    boolean isOpen();

    /**
     * Opens the connection to the database.
     * All the parameters have to be set via the constructor.
     */
    void open() throws ConnectionException;

    /**
     * Closes the connection to the database.
     */
     void close();

    /**
     * Adds an article object to the database
     * @param article The article to add to the database.
     */
    void addArticle(Article article);
}
