package org.utm.lab1.repository.Impl;

import org.utm.lab1.repository.FileManager;
import org.utm.lab1.repository.Repository;

import java.util.List;

public class BatchGraduationRepository implements Repository {

    private final FileManager fileManager = new FileManager("batch_graduation.txt");
    @Override
    public List<String[]> loadData() {
        return fileManager.loadData();
    }
}
