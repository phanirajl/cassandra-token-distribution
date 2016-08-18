package io.smartcat.cassandra.token.distribution.analyzer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Transforms file content into list of string lines.
 */
public class FileProcessor {

    /**
     * Gets files for the given paths.
     * 
     * @param paths Paths of files
     * @return Files
     */
    public static List<File> getFiles(String... paths) {
        List<File> files = new ArrayList<File>();
        for (String path : paths)
            files.add(new File(path));

        return files;
    }

    /**
     * Parses files, splits their content into lines and returns list of lines.
     * 
     * @param paths Paths of files
     * @return List of string lines
     * @throws IOException
     */
    public static List<String> getLines(String... paths) throws IOException {
        List<String> lines = new ArrayList<String>();
        for (File file : getFiles(paths)) {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);

            fis.close();
            br.close();
        }
        return lines;
    }

}
