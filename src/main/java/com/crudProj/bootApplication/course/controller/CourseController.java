package com.crudProj.bootApplication.course.controller;


import com.crudProj.bootApplication.course.model.Course;
import com.crudProj.bootApplication.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v01/api")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<String>create( @RequestBody List<Course> course){
        String response = "course created successfully";

        List<Course>courseList=courseService.createCourse(course);
        if(CollectionUtils.isEmpty(courseList)){
            response = "invalid Request data";
            return new ResponseEntity(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course>update(@RequestBody Course course , @PathVariable(value="id")long id){
        Course updatedCourse = null;
        try{
            updatedCourse=courseService.updateCourse(course,id);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(updatedCourse,HttpStatus.OK);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course>retrieve(@PathVariable(value="id")long id){
        Course retrievedCourse = null;
        try{
            retrievedCourse=courseService.retrieveCourse(id);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(retrievedCourse,HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>>retrieveAll(){
        String response = "course created successfully";
        List<Course> courseList=courseService.retrieveCourses();
        return new ResponseEntity<>(courseList,HttpStatus.OK);

    }
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void>delete(@PathVariable(value="id")long id){
        try{
            courseService.deleteCourse(id);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
