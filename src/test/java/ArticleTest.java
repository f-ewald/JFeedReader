import junit.framework.TestCase;

import java.time.LocalDateTime;

/**
 * Class to test the Article class and its methods.
 */
public class ArticleTest extends TestCase {

    private Article article;
    private LocalDateTime dateTime;

    public void setUp() {
        dateTime = LocalDateTime.now();
        article = new Article("An article", dateTime);
    }

    public void testArticleEqual() {
        Article article1 = new Article("An article", dateTime);
        assertTrue(article1.equals(article));
    }

    public void testArticleNotEqual() {
        Article article1 = new Article("An article2", dateTime);
        assertFalse(article1.equals(article));
    }

}
