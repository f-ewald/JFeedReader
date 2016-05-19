import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to clean Strings from all unnecessary characters.
 */
public class HeadlineFormatter {
    /**
     * The stop words to apply. Either from the template or from a supplied List.
     */
    private List<String> stopWords;

    /**
     * Template for stop words, separated by comma.
     */
    private final String stopWordsTemplate = "and,or,if,also,whether,the";

    /**
     * Default constructor, loads a limited stopword list from a template.
     */
    public HeadlineFormatter() {
        stopWords = Arrays.asList(stopWordsTemplate.split(","));
    }

    /**
     * Takes a list of stop words as an argument and uses it for string processing.
     * @param stopWords List of stop words.
     */
    public HeadlineFormatter(List<String> stopWords) {
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
            if (stopWords.indexOf(tmp) == -1) {
                result = String.format("%1 %2", result, tmp);
            }
        }

        return result;
    }
}
