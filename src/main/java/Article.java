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
     * This is the optimized version which is cleaned for stopwords and any other special chars.
     */
    private String headline;

    /**
     * The original headline as it is delivered by the feed.
     */
    private String headlineOriginal;

    /**
     * The date and time when the article has been published in local time zone.
     */
    public LocalDateTime publishedDateTime;

    /**
     * The date and time when the article has been updated in local time zone.
     * Null, if the article has not been updated.
     */
    public LocalDateTime updatedDateTime;

    /**
     * The URL to the full article.
     */
    public URL url;

    /**
     * The author of the article. Null if no author is provided.
     */
    public String author;

    /**
     * The content or teaser of the article.
     */
    public String content;

    public Article(String headlineOriginal, LocalDateTime publishedDateTime) {
        this.headlineOriginal = headlineOriginal;
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
     * Getter
     * @return the modified headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Setter
     * @param headline the new headline
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadlineOriginal() {
        return headlineOriginal;
    }

    /**
     * Setter
     * @param headlineOriginal the new original headline
     */
    public void setHeadlineOriginal(String headlineOriginal) {
        this.headlineOriginal = headlineOriginal;
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
        document.append("headlineOriginal", headlineOriginal);
        document.append("publishedDateTime", publishedDateTime.format(dateTimeFormatter));
        if (updatedDateTime != null) {
            document.append("updatedDateTime", updatedDateTime.format(dateTimeFormatter));
        }
        document.append("url", url.toString());
        document.append("author", author);
        document.append("content", content);
        return document;
    }
}