package org.utm.lab2.utils.files;

import org.utm.lab2.utils.files.abstracts.FileDetails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TextFileDetails extends FileDetails {

    public TextFileDetails(String folderPath) {
        super(folderPath);
    }

    @Override
    public void displaySpecificInfo(File file) {
        try {
            var lines = Files.readAllLines(file.toPath());
            System.out.println("Line Count: " + lines.size());
            System.out.println("Word Count: " + lines.stream().mapToInt(line -> line.split("\\s+").length).sum());
            System.out.println("Character Count: " + lines.stream().mapToInt(String::length).sum());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

