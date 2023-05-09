package com.crudProj.bootApplication.course.service;

import com.crudProj.bootApplication.course.model.Course;

import java.util.List;
import java.util.Set;

public interface ICourseService {

    List<Course> createCourse(List<Course> course);
    Course retrieveCourse(long id );
    List<Course> retrieveCourses();
    Course updateCourse(Course course,long id);
    void deleteCourse(long id);

}
