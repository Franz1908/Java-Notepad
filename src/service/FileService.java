package service;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

public class FileService {

    public static void saveFile(File file, String text) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.close();
    }

    public static String readFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }


}
