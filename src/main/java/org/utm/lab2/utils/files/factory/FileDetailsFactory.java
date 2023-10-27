package org.utm.lab2.utils.files.factory;


import org.utm.lab2.utils.files.CodeFileDetails;
import org.utm.lab2.utils.files.GenericFileDetails;
import org.utm.lab2.utils.files.ImageFileDetails;
import org.utm.lab2.utils.files.TextFileDetails;
import org.utm.lab2.utils.files.abstracts.FileDetails;

public class FileDetailsFactory {

    private final String folderPath;

    public FileDetailsFactory(String folderPath) {
        this.folderPath = folderPath;
    }

    public FileDetails getFileDetails(String fileName) {
        var extension = getFileExtension(fileName);
        return switch (extension) {
            case "png", "jpg" -> new ImageFileDetails(folderPath);
            case "txt" -> new TextFileDetails(folderPath);
            case "py", "java" -> new CodeFileDetails(folderPath);
            default -> new GenericFileDetails(folderPath);
        };
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}

