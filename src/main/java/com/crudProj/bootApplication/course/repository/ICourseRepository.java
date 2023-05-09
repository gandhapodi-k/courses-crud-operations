package com.crudProj.bootApplication.course.repository;

import com.crudProj.bootApplication.course.model.Course;

import java.util.List;

public interface ICourseRepository {

    List<Course> saveCourse(List<Course> course);
    Course updateCourse(Course course);
    List<Course> getAllCourses();
    void deleteCourseById(long id);


}
