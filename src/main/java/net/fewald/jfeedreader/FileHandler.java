package net.fewald.jfeedreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to handle file access
 */
public class FileHandler {
    public String filename;

    /**
     * Default constructor
     * @param filename
     */
    public FileHandler(String filename) {
        this.filename = filename;
    }

    public void write(String text) {
        boolean error = false;
        if (!isExisting(this.filename)) {
            try {
                create(this.filename);
            }
            catch (IOException exception) {
                exception.printStackTrace();
                error = true;
            }
        }

        if (!error) {
            try {
                FileWriter fileWriter = new FileWriter(filename);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(text);
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isExisting(String filename) {
        File file = new File(filename);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Creates a new file with the given filename.
     * @param filename the filename
     * @throws IOException
     */
    private void create(String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
    }
}
