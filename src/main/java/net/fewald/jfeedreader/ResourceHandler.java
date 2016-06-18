package net.fewald.jfeedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fe on 26.05.16.
 */
public class ResourceHandler {
    public static List<String> getResourceAsList(String filename) throws IOException {
        List<String> result = new LinkedList<String>();
        // Get the resource file as an InputStream
        InputStream is = Program.class.getResourceAsStream(filename);
        // Create a BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        // Read line by line and add to the resulting list.
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    public static HashSet<String> getResourceAsHashSet(String filename) throws IOException {
        HashSet<String> result = new HashSet<String>();
        // Get the resource file as an InputStream
        InputStream is = Program.class.getResourceAsStream(filename);
        // Create a BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        // Read line by line and add to the resulting list.
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
}
