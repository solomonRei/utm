package org.utm.lab1.services;

import org.utm.lab1.models.Student;
import org.utm.lab1.models.mappers.StudentMapper;
import org.utm.lab1.repository.Impl.BatchEnrollmentRepository;
import org.utm.lab1.repository.Impl.BatchGraduationRepository;
import org.utm.lab1.repository.Impl.StudentRepository;
import org.utm.lab1.utils.Logger;
import org.utm.lab1.utils.ParseDate;

import java.util.List;
import java.util.UUID;

public class StudentService {

    private final StudentRepository studentRepository;

    private final BatchEnrollmentRepository batchEnrollmentRepository;

    private final BatchGraduationRepository batchGraduationRepository;

    private final FacultyService facultyService;

    public StudentService() {
        this.studentRepository = new StudentRepository();
        this.batchEnrollmentRepository = new BatchEnrollmentRepository();
        this.batchGraduationRepository = new BatchGraduationRepository();
        this.facultyService = new FacultyService();
    }

    public void saveStudent(String facultyAbbreviation, String firstName, String lastName, String email, String dateOfBirth) {
        var faculty = facultyService.getFacultyIdByAbbreviation(facultyAbbreviation);
        var student = getStudentByEmail(email);
        try {
            if (faculty == null) {
                Logger.log("Faculty with abbreviation " + facultyAbbreviation + " does not exist");
                System.out.println("Faculty with abbreviation " + facultyAbbreviation + " does not exist");
            } else if (student != null) {
                Logger.log("Student with email " + email + " already exists");
                System.out.println("Student with email " + email + " already exists");
            } else {
                var facultyId = faculty.getId();
                Student studentNew = new Student();
                studentNew.setId(UUID.randomUUID().toString());
                studentNew.setFirstName(firstName);
                studentNew.setLastName(lastName);
                studentNew.setEmail(email);
                studentNew.setEnrollmentDate(ParseDate.getCurrentDate());
                studentNew.setDateOfBirth(ParseDate.parseStringToDate(dateOfBirth, ParseDate.dateOfBirthPattern));
                studentNew.setFacultyId(facultyId);
                studentNew.setGraduated(false);
                studentRepository.saveData(studentNew);

                Logger.log("Student " + email + " was successfully saved");
                System.out.println("Student " + email + " was successfully saved");
            }
        } catch (Exception e) {
            Logger.log("Error while saving student: " + email);
            System.out.println("Error while saving student");
        }
    }

    public void batchEnrollStudentsFromFile() {
        var partsData = batchEnrollmentRepository.loadData();

        if (partsData.isEmpty()) {
            Logger.log("No students to enroll; Batch enrollment file is empty");
            System.out.println("No students to enroll");
            return;
        }
        Logger.log("Starting batch enroll operation");
        var studentsBatch = StudentMapper.mapListBatchToStudentListBatchDto(partsData);
        for (var student : studentsBatch) {
            saveStudent(student.getAbbreviation(), student.getFirstName(), student.getLastName(), student.getEmail(), ParseDate.parseDateToString(student.getDateOfBirth(), ParseDate.dateOfBirthPattern));
        }
    }

    public void batchGraduationStudentsFromFile() {
        var partsData = batchGraduationRepository.loadData();

        if (partsData.isEmpty()) {
            Logger.log("No students to graduate; Batch graduation file is empty");
            System.out.println("No students to graduate");
            return;
        }
        Logger.log("Starting batch graduation operation");
        var studentsBatch = StudentMapper.mapListBatchToStudentListBatchDto(partsData);
        for (var student : studentsBatch) {
            graduateStudentFromUniversity(student.getEmail());
        }
    }

    public void graduateStudentFromUniversity(String email) {
        var student = getStudentByEmail(email);
        if (student == null) {
            System.out.println("Student with email " + email + " does not exist");
            Logger.log("Can’t graduate student: " + email + "Student with email " + email + " does not exist");
        } else if (student.getGraduated()) {
            System.out.println("Error: Student with email " + email + " is already graduated");
            Logger.log("Can’t graduate student: " + email + "Student with email " + email + " is already graduated");
        } else {
            var students = getAllStudents();
            var updatedStudents = students.stream()
                    .peek(st -> {
                        if (st.getEmail().equals(email)) {
                            st.setGraduated(true);
                        }
                    })
                    .toList();

            studentRepository.overrideData(updatedStudents);
            Logger.log("Student " + email + " was successfully graduated");
            System.out.println("Student " + email + " was successfully graduated");
        }
    }

    public List<Student> getAllStudents() {
        var partsData = studentRepository.loadData();
        return StudentMapper.mapListToStudentList(partsData);
    }

    public Student getStudentByEmail(String email) {
        var students = getAllStudents();
        return students.stream()
                .filter(student -> student.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
