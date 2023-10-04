package org.utm.lab1.repository.Impl;

import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.models.Faculty;
import org.utm.lab1.models.dto.FacultyDto;
import org.utm.lab1.repository.FileManager;
import org.utm.lab1.repository.Repository;

import java.util.List;

@Component
public class FacultyRepository implements Repository {

    private final FileManager fileManager = new FileManager("faculties.txt");

    @Override
    public List<String[]> loadData() {
        return fileManager.loadData();
    }

    public void saveData(FacultyDto facultyDto) {
        String formattedData = facultyDto.getId() + "|" + facultyDto.getName() + "|" +
                facultyDto.getAbbreviation() + "|" + facultyDto.getStudyField().name() + "\n";

        fileManager.saveData(formattedData, true);
    }


}
