package com.crudProj.bootApplication.course.model;

import java.util.Objects;

public class Course {

    private Long id;
    private String courseName;
    private String instructor;
    private int courseDuration;

    public Course() {
    }

    public Course(Long id, String courseName, String instructor, int courseDuration) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseDuration = courseDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
