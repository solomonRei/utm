package org.utm.lab1.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = "src/main/resources/" + filePath;
    }

    public void saveData(String data, boolean append) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> loadData() {

        List<String[]> partsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                partsList.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partsList;
    }


}

