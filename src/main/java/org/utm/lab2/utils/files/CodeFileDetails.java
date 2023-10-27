package org.utm.lab2.utils.files;

import org.utm.lab2.utils.files.abstracts.FileDetails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class CodeFileDetails extends FileDetails {

    public CodeFileDetails(String folderPath) {
        super(folderPath);
    }

    @Override
    public void displaySpecificInfo(File file) {
        System.out.println("Line Count: " + getLineCount(file));
        System.out.println("Class Count: " + getClassCount(file));
        System.out.println("Method Count: " + getMethodCount(file));
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
