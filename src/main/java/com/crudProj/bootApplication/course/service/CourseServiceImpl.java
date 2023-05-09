package com.crudProj.bootApplication.course.service;

import com.crudProj.bootApplication.course.model.Course;
import com.crudProj.bootApplication.course.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public List<Course> createCourse(List<Course> course) {

        return  courseRepository.saveCourse(course);
    }

    @Override
    public Course retrieveCourse(long id) {

        List<Course> courseList=courseRepository.getAllCourses();

        List<Course> resultCourses=courseList.stream().filter(course -> course.getId().equals(id))
                .collect(Collectors.toList());
        if (resultCourses.size() != 1) {
            throw new IllegalStateException("No Course found with given id "+id);
        }
        return resultCourses.get(0);
    }

    @Override
    public List<Course> retrieveCourses() {

        return courseRepository.getAllCourses();
    }

    @Override
    public Course updateCourse(Course course, long id) {
        Course retrieveCourse=this.retrieveCourse(id);
        Course rtnCourse=null;
        if (null != retrieveCourse){
            course.setId(id);
            rtnCourse=courseRepository.updateCourse(course);
        }
        return rtnCourse;
    }

    @Override
    public void deleteCourse(long id) {
        Course retrieveCourse=this.retrieveCourse(id);
        if (null != retrieveCourse){
            courseRepository.deleteCourseById(id);
        }
    }
}
