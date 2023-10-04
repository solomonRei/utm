package org.utm.lab1.models.mappers;

import org.utm.lab1.enums.StudyField;
import org.utm.lab1.models.Faculty;
import org.utm.lab1.models.Student;
import org.utm.lab1.models.dto.FacultyDto;

import java.util.ArrayList;
import java.util.List;

public class FacultyMapper {

    public static Faculty mapToFacultyEntityFromFacultyDto(FacultyDto facultyDto, List<Student> students) {
        Faculty faculty = new Faculty();
        faculty.setId(facultyDto.getId());
        faculty.setName(facultyDto.getName());
        faculty.setAbbreviation(facultyDto.getAbbreviation());
        faculty.setStudyField(facultyDto.getStudyField());
        faculty.setStudents(students);

        return faculty;
    }

    public static List<FacultyDto> mapListToFacultyDtoList(List<String[]> partsList) {
        List<FacultyDto> facultyDtoList = new ArrayList<>();

        for (String[] parts : partsList) {
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setId(parts[0]);
            facultyDto.setName(parts[1]);
            facultyDto.setAbbreviation(parts[2]);
            facultyDto.setStudyField(StudyField.valueOf(parts[3]));
            facultyDtoList.add(facultyDto);
        }

        return facultyDtoList;
    }

}
