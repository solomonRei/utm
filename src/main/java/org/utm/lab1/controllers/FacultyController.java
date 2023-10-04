package org.utm.lab1.controllers;

import org.utm.lab1.factory.annotations.Command;
import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.services.FacultyService;
import org.utm.lab1.utils.ConsoleOutputMessages;
import org.utm.lab1.utils.Logger;

@Component
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController() {
        this.facultyService = new FacultyService();
    }

    @Command(name = "f")
    public void showFacultiesCommand() {
        ConsoleOutputMessages.facultyOperationMessage();
    }

    @Command(name = "nf")
    public void createFaculty(String FacultyName, String abbreviation, String studyField) {
        facultyService.saveFaculty(FacultyName, abbreviation, studyField);
    }

    @Command(name = "ss")
    public void searchStudentBelongingToFaculty(String studentEmail) {
        var faculty = facultyService.getStudentBelongingToFaculty(studentEmail);

        if (faculty == null) {
            System.out.println("Student with email " + studentEmail + " does not belong to any faculty");
        } else {
            System.out.println("Student with email " + studentEmail + " belongs to faculty " + faculty.getName() + " (" + faculty.getAbbreviation() + ")");
        }

    }

    @Command(name = "bf")
    public void isStudentBelongsToFaculty(String facultyAbbreviation, String studentEmail) {
        var faculty = facultyService.getStudentBelongingToFaculty(studentEmail);

        if (faculty == null || !faculty.getAbbreviation().equals(facultyAbbreviation)) {
            System.out.println("Student with email " + studentEmail + " does not belong to this faculty");
        } else {
            System.out.println("Student with email " + studentEmail + " belongs to this faculty");
        }

    }

    @Command(name = "df")
    public void displayFaculties() {
        var faculties = facultyService.getAllFaculties();

        if (faculties.isEmpty()) {
            System.out.println("No faculties found:");
        } else {
            System.out.println("List of all faculties: ");
            faculties.forEach(System.out::println);
        }
    }

    @Command(name = "dff")
    public void displayFaculty(String studyField) {
        var facultiesFiltered = facultyService.getAllFacultiesByField(studyField);

        if (facultiesFiltered.isEmpty()) {
            System.out.println("No faculties found:");
        } else {
            System.out.println("List of all faculties that belong to: " + studyField + " field");
            facultiesFiltered.forEach(System.out::println);
        }
    }

    @Command(name = "de")
    public void displayEnrolledStudents(String facultyAbbreviation) {
        Logger.log("Getting the list of students enrolled in university");
        var students = facultyService.getStudentsEnrolledToFaculty(facultyAbbreviation);
        if (students == null || students.size() == 0) {
            System.out.println("No students enrolled in university");
            Logger.log("No students enrolled in university");
        } else {
            System.out.println("List of students enrolled in university:");
            students.forEach(System.out::println);
        }
    }

    @Command(name = "dg")
    public void displayGraduatedStudents(String facultyAbbreviation) {
        Logger.log("Getting the list of students graduated from university");
        var students = facultyService.getStudentsGraduatedFromFaculty(facultyAbbreviation);
        if (students == null || students.size() == 0) {
            System.out.println("No students graduated from university");
            Logger.log("No students graduated from university");
        } else {
            System.out.println("The list of students graduated from university");
            students.forEach(System.out::println);
        }
    }
}
