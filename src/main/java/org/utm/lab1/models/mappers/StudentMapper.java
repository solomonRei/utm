package org.utm.lab1.models.mappers;

import org.utm.lab1.models.Student;
import org.utm.lab1.models.dto.StudentBatchDto;
import org.utm.lab1.utils.ParseDate;

import java.util.ArrayList;
import java.util.List;

public class StudentMapper {

    public static List<Student> mapListToStudentList(List<String[]> partsList) {
        List<Student> studentList = new ArrayList<>();

        for (String[] parts : partsList) {
            Student student = new Student();
            student.setId(parts[0]);
            student.setFirstName(parts[1]);
            student.setLastName(parts[2]);
            student.setEmail(parts[3]);
            student.setEnrollmentDate(ParseDate.parseStringToDate(parts[4], ParseDate.enrollmentDatePattern));
            student.setDateOfBirth(ParseDate.parseStringToDate(parts[5], ParseDate.dateOfBirthPattern));
            student.setFacultyId(parts[6]);
            student.setGraduated(!parts[7].isEmpty() && Boolean.parseBoolean(parts[7]));
            studentList.add(student);
        }

        return studentList;
    }

    public static List<StudentBatchDto> mapListBatchToStudentListBatchDto(List<String[]> partsList) {
        List<StudentBatchDto> studentListBatch = new ArrayList<>();

        for (String[] parts : partsList) {
            StudentBatchDto student = new StudentBatchDto();
            student.setAbbreviation(parts[0]);
            student.setFirstName(parts[1]);
            student.setLastName(parts[2]);
            student.setEmail(parts[3]);
            student.setDateOfBirth(ParseDate.parseStringToDate(parts[4], ParseDate.dateOfBirthPattern));
            studentListBatch.add(student);
        }

        return studentListBatch;
    }

}
