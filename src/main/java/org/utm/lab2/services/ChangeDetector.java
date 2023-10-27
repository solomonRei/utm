package org.utm.lab2.services;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

abstract public class ChangeDetector {

    protected final String folderPath;

    public ChangeDetector(String folderPath) {
        this.folderPath = folderPath;
    }

    protected File[] getListOfFiles() {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The specified path is not a valid directory: " + folderPath);
            return null;
        }

        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            System.out.println("Unable to list files from the directory: " + folderPath);
            return null;
        }

        return listOfFiles;
    }

    public abstract void detectChanges();
}
