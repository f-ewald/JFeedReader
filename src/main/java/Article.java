import com.google.gson.Gson;
import org.bson.Document;
import java.net.URL;
import java.time.LocalDateTime;


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
     * Gets the current article as a JSON object.
     * @return article as json representation
     */
    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns the article as a Document
     * @return the current article as a org.bson.Document
     */
    public Document getDocument() {
        Document document = new Document();
        document.append("headline", headline);
        document.append("publishedDateTime", publishedDateTime);
        document.append("updatedDateTime", updatedDateTime);
        document.append("url", url);
        document.append("author", author);
        document.append("content", content);
        return document;
    }


}
