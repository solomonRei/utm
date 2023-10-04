package org.utm.lab1.controllers;

import org.utm.lab1.factory.annotations.Command;
import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.services.StudentService;
import org.utm.lab1.utils.ConsoleOutputMessages;

@Component
public class StudentController {

    private final StudentService studentService;

    public StudentController() {
        this.studentService = new StudentService();
    }

    @Command(name = "g")
    public void showStudentsCommands() {
        ConsoleOutputMessages.printStudentManagementMessage();
    }

    @Command(name = "ns")
    public void createStudent(String facultyAbbreviation, String firstName, String lastName, String email, String dateOfBirth) {
        studentService.saveStudent(facultyAbbreviation, firstName, lastName, email, dateOfBirth);
    }

    @Command(name = "gs")
    public void graduateStudent(String email) {
        studentService.graduateStudentFromUniversity(email);
    }

    @Command(name = "bes")
    public void batchEnrollStudents() {
        studentService.batchEnrollStudentsFromFile();
    }

    @Command(name = "bgs")
    public void batchGraduationStudents() {
        studentService.batchGraduationStudentsFromFile();
    }

}
