package org.utm.lab2.utils.files.abstracts;

import org.utm.lab2.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileDetails {

    protected String folderPath;

    public FileDetails(String folderPath) {
        this.folderPath = folderPath;
    }

    public final void info(String fileName) {
        var file = new File(folderPath + "/" + fileName);
        if (file.exists()) {
            displayCommonInfo(file);
            displaySpecificInfo(file);
        } else {
            System.out.println(fileName + " does not exist.");
        }
    }

    protected void displayCommonInfo(File file) {
        System.out.println("File Name: " + file.getName());
        try {
            var attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            System.out.println("Created Date: " + DateUtils.formatTimestamp(attrs.creationTime().toMillis()));
            System.out.println("Updated Date: " + DateUtils.formatTimestamp(file.lastModified()));
            System.out.println("File Extension: " + getFileExtension(file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error fetching file attributes.");
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    protected abstract void displaySpecificInfo(File file);
}



