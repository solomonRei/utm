package org.utm.lab1.repository.Impl;

import org.utm.lab1.repository.FileManager;
import org.utm.lab1.repository.Repository;

import java.util.List;

public class BatchEnrollmentRepository implements Repository {

    private final FileManager fileManager = new FileManager("batch_enrollment.txt");

    @Override
    public List<String[]> loadData() {
        return fileManager.loadData();
    }
}
