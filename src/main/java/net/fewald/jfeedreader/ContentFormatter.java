package net.fewald.jfeedreader;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created by fe on 26.05.16.
 */
public class ContentFormatter {
    /**
     * Cleans a String from all the HTML and also makes it safe to
     * @param text The text to clean
     * @return
     */
    public static String cleanHtml(String text) {
        String clean = Jsoup.clean(text, Whitelist.basic());
        return Jsoup.parse(clean).text();
    }
}
