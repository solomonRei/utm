package org.utm.lab2.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileChangeDetector extends ChangeDetector {

    private final String folderPath;

    private final Map<String, Long> fileLastModifiedMap = new HashMap<>();

    private List<String> changeMessages = new ArrayList<>();

    private long snapshotTime = System.currentTimeMillis();

    public FileChangeDetector(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public void detectChanges() {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The specified path is not a valid directory: " + folderPath);
            return;
        }

        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            System.out.println("Unable to list files from the directory: " + folderPath);
            return;
        }

        changeMessages = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                long lastModified = file.lastModified();
                if (fileLastModifiedMap.containsKey(file.getName())) {
                    if (fileLastModifiedMap.get(file.getName()) != lastModified) {
                        changeMessages.add(file.getName() + " - Changed");
                        fileLastModifiedMap.put(file.getName(), lastModified);
                    } else {
                        changeMessages.add(file.getName() + " - No Change");
                    }
                } else {
                    changeMessages.add(file.getName() + " - Added");
                    fileLastModifiedMap.put(file.getName(), lastModified);
                }
            }
        }

        for (String fileName : new HashMap<>(fileLastModifiedMap).keySet()) {
            File file = new File(folderPath + "/" + fileName);
            if (!file.exists()) {
                changeMessages.add(fileName + " - Deleted");
                fileLastModifiedMap.remove(fileName);
            }
        }
    }

    public void commit() {
        detectChanges();
        snapshotTime = System.currentTimeMillis();
        System.out.println("Created Snapshot at: " + snapshotTime);
        for (String message : changeMessages) {
            System.out.println(message);
        }


    }

    public void info(String fileName) {
        File file = new File(folderPath + "/" + fileName);
        if (file.exists()) {
            System.out.println("File Name: " + file.getName());
            System.out.println("Last Modified: " + file.lastModified());
        } else {
            System.out.println(fileName + " does not exist.");
        }
    }

    public void status() {
        if (fileLastModifiedMap.isEmpty()) {
            System.out.println("No snapshots taken. You have to write commit first.");
            return;
        }

        System.out.println("Created Snapshot at: " + snapshotTime);
        for (String message : changeMessages) {
            System.out.println(message);
        }
    }

}
