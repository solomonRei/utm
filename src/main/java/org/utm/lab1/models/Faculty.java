package org.utm.lab1.models;


import org.utm.lab1.enums.StudyField;
import org.utm.lab1.factory.annotations.Component;

import java.util.List;

@Component
public class Faculty extends Entity {

    private String name;

    private String abbreviation;

    private List<Student> students;

    private StudyField studyField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public StudyField getStudyField() {
        return studyField;
    }

    public void setStudyField(StudyField studyField) {
        this.studyField = studyField;
    }
}
