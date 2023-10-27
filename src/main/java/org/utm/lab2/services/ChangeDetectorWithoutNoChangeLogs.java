package org.utm.lab2.services;

import org.utm.lab2.services.abstracts.AbstractChangeDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ChangeDetectorWithoutNoChangeLogs extends AbstractChangeDetector {

    public ChangeDetectorWithoutNoChangeLogs(String folderPath) {
        super(folderPath);
    }

    @Override
    public void detectChanges() {
        File[] listOfFiles = getListOfFiles();
        if (listOfFiles == null) return;

        changeMessages = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                long lastModified = file.lastModified();
                if (fileLastModifiedMap.containsKey(file.getName()) && fileLastModifiedMap.get(file.getName()) != lastModified) {
                    changeMessages.add(file.getName() + " - Changed");
                } else if (!fileLastModifiedMap.containsKey(file.getName())) {
                    changeMessages.add(file.getName() + " - Added");
                }
            }
        }

        for (String fileName : new HashMap<>(fileLastModifiedMap).keySet()) {
            File file = new File(folderPath + "/" + fileName);
            if (!file.exists()) {
                changeMessages.add(fileName + " - Deleted");
            }
        }
    }

    public void clearChangeMessages() {
        changeMessages.clear();
    }
}
