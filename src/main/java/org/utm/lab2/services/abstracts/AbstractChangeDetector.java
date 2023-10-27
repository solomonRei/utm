package org.utm.lab2.services.abstracts;

import org.utm.lab2.utils.DateUtils;
import org.utm.lab2.utils.files.abstracts.FileDetails;
import org.utm.lab2.utils.files.factory.FileDetailsFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractChangeDetector extends ChangeDetector {

    protected List<String> changeMessages = new ArrayList<>();

    protected long snapshotTime = System.currentTimeMillis();

    public long getSnapshotTime() {
        return snapshotTime;
    }

    protected final Map<String, Long> fileLastModifiedMap = new HashMap<>();

    public AbstractChangeDetector(String folderPath) {
        super(folderPath);
    }

    public List<String> getChangeMessages() {
        return changeMessages;
    }

    public boolean checkIfFileLastModifiedMapIsEmpty() {
        return fileLastModifiedMap.isEmpty();
    }

    @Override
    public abstract void detectChanges();


    public void commit() {
        File[] listOfFiles = getListOfFiles();
        if (listOfFiles == null) return;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                long lastModified = file.lastModified();
                fileLastModifiedMap.put(file.getName(), lastModified);
            }
        }

        snapshotTime = System.currentTimeMillis();
    }

    public void info(String fileName) {
        FileDetailsFactory factory = new FileDetailsFactory(folderPath);
        FileDetails details = factory.getFileDetails(fileName);
        details.info(fileName);
    }

    public void status() {
        if (checkIfFileLastModifiedMapIsEmpty()) {
            System.out.println("No snapshots taken. You have to write commit first.");
            return;
        }

        detectChanges();

        System.out.println("Created Snapshot at: " + DateUtils.formatTimestamp(snapshotTime));
        changeMessages.forEach(System.out::println);
    }
}
