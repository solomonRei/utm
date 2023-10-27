package org.utm.lab2.utils.files;

import org.utm.lab2.utils.files.abstracts.FileDetails;

import java.io.File;

public class GenericFileDetails extends FileDetails {

    public GenericFileDetails(String folderPath) {
        super(folderPath);
    }

    @Override
    protected void displaySpecificInfo(File file) {

    }
}
