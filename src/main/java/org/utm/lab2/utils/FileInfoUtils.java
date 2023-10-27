package org.utm.lab2.utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;

public class FileInfoUtils {

    private final String folderPath;

    public FileInfoUtils(String folderPath) {
        this.folderPath = folderPath;
    }

    public void info(String fileName) {
        var file = new File(folderPath + "/" + fileName);
        if (file.exists()) {
            System.out.println("File Name: " + file.getName());
            try {
                var attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                System.out.println("Created Date: " + DateUtils.formatTimestamp(attrs.creationTime().toMillis()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error fetching created date.");
            }

            System.out.println("Updated Date: " + DateUtils.formatTimestamp(file.lastModified()));

            var extension = getFileExtension(file);
            System.out.println("File Extension: " + extension);


            switch (extension) {
                case "png", "jpg" -> {
                    var dimensions = getImageDimensions(file);
                    System.out.println("Image Size: " + dimensions);
                }
                case "txt" -> {
                    try {
                        var lines = Files.readAllLines(file.toPath());
                        System.out.println("Line Count: " + lines.size());
                        System.out.println("Word Count: " + lines.stream().mapToInt(line -> line.split("\\s+").length).sum());
                        System.out.println("Character Count: " + lines.stream().mapToInt(String::length).sum());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "py", "java" -> {
                    System.out.println("Line Count: " + getLineCount(file));
                    System.out.println("Class Count: " + getClassCount(file));
                    System.out.println("Method Count: " + getMethodCount(file));
                }
            }

        } else {
            System.out.println(fileName + " does not exist.");
        }
    }

    private String getFileExtension(File file) {
        var fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    private String getImageDimensions(File file) {
        try {
            var image = ImageIO.read(file);
            return image.getWidth() + "x" + image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private int getLineCount(File file) {
        try {
            return (int) Files.lines(file.toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getClassCount(File file) {
        try {
            var lines = Files.readAllLines(file.toPath());
            var content = String.join(" ", lines);

            var classPattern = Pattern.compile("\\bclass\\b");
            var classMatcher = classPattern.matcher(content);

            int count = 0;
            while (classMatcher.find()) {
                count++;
            }
            return count;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getMethodCount(File file) {
        try {
            var lines = Files.readAllLines(file.toPath());
            var content = String.join(" ", lines);

            var methodPattern = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *\\{? *\\n?");
            var methodMatcher = methodPattern.matcher(content);

            int count = 0;
            while (methodMatcher.find()) {
                count++;
            }
            return count;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
