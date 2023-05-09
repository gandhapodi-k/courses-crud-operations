package com.crudProj.bootApplication.course.repository;

import com.crudProj.bootApplication.course.model.Course;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class CourseRepositoryNodbImpl implements ICourseRepository {

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private List<Course> courseMasterList = new ArrayList<>();

    @Override
    public List<Course> saveCourse(List<Course> course) {

        if(!CollectionUtils.isEmpty(course)){
            course.stream().forEach(courseObj->{
                courseObj.setId((long) ID_GENERATOR.getAndIncrement());
                courseMasterList.add(courseObj);
            });
        }
        return courseMasterList;
    }

    @Override
    public Course updateCourse(Course course) {
        Course updatedCourse = new Course();
        for(int i=0;i < courseMasterList.size();i++){
            if (courseMasterList.contains(course)) {
                updatedCourse.setId(course.getId());
                updatedCourse.setCourseName(course.getCourseName());
                updatedCourse.setCourseDuration(course.getCourseDuration());
                updatedCourse.setInstructor(course.getInstructor());
                courseMasterList.remove(course);
                courseMasterList.add(updatedCourse);
            }
        }
        return updatedCourse;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = List.of(new Course((long) ID_GENERATOR.getAndIncrement(), "Java", "karthik", 2),
                new Course((long) ID_GENERATOR.getAndIncrement(), "Python", "george", 3),
                new Course((long) ID_GENERATOR.getAndIncrement(), "sql", "david", 2));
        for (Course course : list) {
            courseMasterList.add(course);
        }
        List<Course>sortedCourses=courseMasterList.stream().sorted(Comparator.comparing(Course::getId)).collect(Collectors.toList());
        return sortedCourses;
    }

    @Override
    public void deleteCourseById(long id) {

        courseMasterList.removeIf(course->course.getId().equals(id));

    }
}
