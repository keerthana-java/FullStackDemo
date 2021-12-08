package com.zipcode.FullStackDemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.zipcode.FullStackDemo.repository.StudentRepo;
import com.zipcode.FullStackDemo.entity.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController - Used to create RESTful web services using Spring MVC.
 * Takes care of mapping request data to the defined request handler method.
 * It adds the @Controller and @ResponseBody annotations
 **/
@RestController
@RequestMapping("/students") // localhost:8080/students
public class StudentController {
    

    private final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    /**
     * localhost:8080/students,
     * Get all students,
     * returns array of student objects
     **/
    @GetMapping
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    /**
     * localhost:8080/students/1,
     * Get student by id,
     * returns student object with matching ID
     **/
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    /**
     * localhost:8080/students,
     * Create a new student,
     * send student object and receive same object with generated ID
     **/
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws URISyntaxException {
       log.info("Request to create student");
       Student savedStudent = studentRepo.save(student);
       return ResponseEntity.created(new URI("/students" + savedStudent.getId())).body(savedStudent);     
    }

    /**
     * localhost:8080/students/1,
     * Update a student by id,
     * send new student object with ID to apply to
     **/
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        log.info("Request to update student");
        Student studentToUpdate = studentRepo.findById(id).orElseThrow(RuntimeException::new);
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setCohort(student.getCohort());
        studentRepo.save(studentToUpdate);
        return ResponseEntity.ok().build();
    }

    /**
     * localhost:8080/students/1,
     * Delete a student by id,
     * removes student object by ID sent
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        log.info("Request to delete student");
        studentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
