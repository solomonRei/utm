package org.utm.lab1.services;

import org.utm.lab1.enums.StudyField;
import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.models.Faculty;
import org.utm.lab1.models.Student;
import org.utm.lab1.models.dto.FacultyDto;
import org.utm.lab1.models.mappers.FacultyMapper;
import org.utm.lab1.models.mappers.StudentMapper;
import org.utm.lab1.repository.Impl.FacultyRepository;
import org.utm.lab1.repository.Impl.StudentRepository;
import org.utm.lab1.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyService() {
        this.facultyRepository = new FacultyRepository();
        this.studentRepository = new StudentRepository();
    }

    public List<FacultyDto> getAllFaculties() {
        var partsData = facultyRepository.loadData();
        return FacultyMapper.mapListToFacultyDtoList(partsData);
    }

    public boolean isValidStudyField(String studyField) {
        try {
            StudyField.valueOf(studyField);
            return true;
        } catch (IllegalArgumentException e) {
            Logger.log("Invalid studyField: " + studyField);
            System.out.println("Invalid studyField: " + studyField);
            return false;
        }
    }

    public void saveFaculty(String FacultyName, String abbreviation, String studyField) {
        if (isValidStudyField(studyField)) {
            if (getFacultyIdByAbbreviation(abbreviation) != null) {
                Logger.log("Faculty with abbreviation " + abbreviation + " already exists");
                System.out.println("Faculty with abbreviation " + abbreviation + " already exists");
                return;
            }

            StudyField studyFieldEnum = StudyField.valueOf(studyField);

            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setId(UUID.randomUUID().toString());
            facultyDto.setName(FacultyName);
            facultyDto.setAbbreviation(abbreviation);
            facultyDto.setStudyField(studyFieldEnum);

            facultyRepository.saveData(facultyDto);

            Logger.log("Faculty created successfully.");
            System.out.println("Faculty created successfully.");
        }
    }

    public List<FacultyDto> getAllFacultiesByField(String studyField) {
        if (isValidStudyField(studyField)) {
            var faculties = getAllFaculties();
            return faculties.stream()
                    .filter(faculty -> faculty.getStudyField().name().equals(studyField))
                    .toList();
        }
        return new ArrayList<>();
    }

    public FacultyDto getFacultyIdByAbbreviation(String facultyAbbreviation) {
        var faculties = getAllFaculties();
        return faculties.stream()
                .filter(faculty -> faculty.getAbbreviation().equals(facultyAbbreviation))
                .findFirst()
                .orElse(null);
    }

    public Faculty getFacultyByAbbreviation(String facultyAbbreviation) {
        var partsData = studentRepository.loadData();
        var students = StudentMapper.mapListToStudentList(partsData);
        var faculty = getFacultyIdByAbbreviation(facultyAbbreviation);

        if (faculty != null) {
            var studentsEnrolledToFaculty = students.stream()
                    .filter(student -> student.getFacultyId().equals(faculty.getId()))
                    .toList();

            return FacultyMapper.mapToFacultyEntityFromFacultyDto(faculty, studentsEnrolledToFaculty);
        } else {
            System.out.println("Faculty with abbreviation " + facultyAbbreviation + " does not exist");
            return null;
        }
    }

    public List<Student> getStudentsEnrolledToFaculty(String facultyAbbreviation) {
        var faculty = getFacultyByAbbreviation(facultyAbbreviation);
        if (faculty != null && faculty.getStudents() != null) {
            return faculty.getStudents().stream()
                    .filter(student -> !student.getGraduated())
                    .toList();
        }

        return null;
    }

    public List<Student> getStudentsGraduatedFromFaculty(String facultyAbbreviation) {
        var faculty = getFacultyByAbbreviation(facultyAbbreviation);
        if (faculty != null && faculty.getStudents() != null) {
            return faculty.getStudents().stream()
                    .filter(Student::getGraduated)
                    .toList();
        }

        return null;
    }

    public Faculty getStudentBelongingToFaculty(String studentEmail) {
        var faculties = getAllFaculties();
        List<Faculty> facultiesEntities = new ArrayList<>();
        for (var faculty : faculties) {
            facultiesEntities.add(getFacultyByAbbreviation(faculty.getAbbreviation()));
        }

        return facultiesEntities.stream()
                .filter(faculty -> {
                    var students = faculty.getStudents();
                    if (students == null) {
                        return false;
                    }
                    return students.stream()
                            .anyMatch(student -> student.getEmail().equals(studentEmail) && !student.getGraduated());
                })
                .findFirst()
                .orElse(null);
    }

}
