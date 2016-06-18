package net.fewald.jfeedreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fe on 18.06.16.
 */
public class FeedFileHandler {
    public List<Feed> getFeedList(String filename) throws IOException {
        List<Feed> feedList = new ArrayList<Feed>();
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split(";");
            if (splitLine.length != 2) {
                throw new IllegalArgumentException("The line is not formatted correctly.");
            }
            Feed feed = new Feed(splitLine[0], new URL(splitLine[1]));
            feedList.add(feed);
        }
        return feedList;
    }
}
