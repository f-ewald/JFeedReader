package net.fewald.jfeedreader;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Class to clean Strings from all unnecessary characters.
 */
public class HeadlineFormatter {
    /**
     * The stop words to apply. Either from the template or from a supplied List.
     */
    private HashSet<String> stopWords;

    /**
     * Template for stop words, separated by comma.
     */
    private final String stopWordsTemplate = "and,or,if,also,whether,the";

    /**
     * Default constructor, loads a limited stopword list from a template.
     */
    public HeadlineFormatter() {
        stopWords = new HashSet<String>();
        for (String item : Arrays.asList(stopWordsTemplate.split(","))) {
            stopWords.add(item);
        }
    }

    /**
     * Takes a list of stop words as an argument and uses it for string processing.
     * @param stopWords List of stop words.
     */
    public HeadlineFormatter(HashSet<String> stopWords) {
        this.stopWords = stopWords;
    }

    /**
     * Cleans a String from all the unnecessary characters and whitespaces.
     * @param inputString The string to clean.
     * @return The cleaned string.
     */
    public String getCleanString(String inputString) {
        String preparedString;
        String result = "";
        // Kill all the special characters, like .,;:!?
        // (?i) makes the regex case insensitive
        preparedString = inputString.replaceAll("(?i)[^a-z0-9 ]+", " ");

        // Split by whitespace
        String[] inputArray = preparedString.split(" ");

        // Loop over the String and add it to the output if it does not match any of the stopWords.
        for (String str : inputArray) {
            String tmp = str.toLowerCase().trim();
            // If the set does NOT contain the current word...
            if (!stopWords.contains(tmp)) {
                result = String.format("%1s %2s", result, tmp).trim();
            }
        }

        return result;
    }
}
