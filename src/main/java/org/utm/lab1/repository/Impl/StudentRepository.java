package org.utm.lab1.repository.Impl;

import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.models.Student;
import org.utm.lab1.repository.FileManager;
import org.utm.lab1.repository.Repository;
import org.utm.lab1.utils.ParseDate;

import java.util.List;

@Component
public class StudentRepository implements Repository {

    private final FileManager fileManager = new FileManager("students.txt");

    @Override
    public List<String[]> loadData() {
        return fileManager.loadData();
    }

    public void saveData(Student student) {
        String formattedData = student.getId() + "|" + student.getFirstName() + "|" +
                student.getLastName() + "|" + student.getEmail() + "|" +
                ParseDate.parseDateToString(student.getEnrollmentDate(), ParseDate.enrollmentDatePattern) + "|" + ParseDate.parseDateToString(student.getDateOfBirth(), ParseDate.dateOfBirthPattern) + "|" +
                student.getFacultyId() + "|" + student.getGraduated() + "\n";
        fileManager.saveData(formattedData, true);
    }

    public void overrideData(List<Student> students) {
        StringBuilder formattedData = new StringBuilder();
        for (Student student : students) {
            String studentFormattedDate = student.getId() + "|" + student.getFirstName() + "|" +
                    student.getLastName() + "|" + student.getEmail() + "|" +
                    ParseDate.parseDateToString(student.getEnrollmentDate(), ParseDate.enrollmentDatePattern) + "|" + ParseDate.parseDateToString(student.getDateOfBirth(), ParseDate.dateOfBirthPattern) + "|" +
                    student.getFacultyId() + "|" + student.getGraduated() + "\n";
            formattedData.append(studentFormattedDate);
        }
        fileManager.saveData(formattedData.toString(), false);
    }
}
