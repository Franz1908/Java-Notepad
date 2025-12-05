package service;

import java.io.*;

/**
 * Service class that handles file I/O operations for text files.
 * Provides static methods for saving and reading text files.
 */
public class FileService {

    /**
     * Saves text content to a file.
     *
     * @param file  the target file
     * @param text  the text content to save
     * @throws IOException if writing to the file fails
     */
    public static void saveFile(File file, String text) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.close();
    }

    /**
     * Reads text content from a file.
     * Preserves line separators appropriate for the current OS.
     *
     * @param file  the file to read
     * @return      the file content as a string
     * @throws IOException if reading the file fails
     */
    public static String readFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                // Append line separator to preserve line breaks
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();
        }
    }
}
