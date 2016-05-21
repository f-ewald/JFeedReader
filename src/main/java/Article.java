import com.google.gson.Gson;
import org.bson.Document;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Article from RSS/Atom feed.
 */
public class Article {
    /**
     * Headline of the article
     */
    public String headline;
    public LocalDateTime publishedDateTime;
    public LocalDateTime updatedDateTime;
    public URL url;
    public String author;
    public String content;

    public Article(String headline, LocalDateTime publishedDateTime) {
        this.headline = headline;
        this.publishedDateTime = publishedDateTime;
    }

    /**
     * Checks, if a article equals another one based on headline and published time.
     * @param a The other article
     * @return true if both articles are equal, otherwise false
     */
    public boolean equals(Article a) {
        if (a.headline.equals(headline) && a.publishedDateTime.equals(publishedDateTime)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the article as a Document
     * @return the current article as a org.bson.Document
     */
    public Document getDocument() {
        // Use a DateTimeFormatter to convert the LocalDateTime objects to a mongo readable format.
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Document document = new Document();
        document.append("headline", headline);
        document.append("publishedDateTime", publishedDateTime.format(dateTimeFormatter));
        document.append("updatedDateTime", updatedDateTime.format(dateTimeFormatter));
        document.append("url", url);
        document.append("author", author);
        document.append("content", content);
        return document;
    }


}
