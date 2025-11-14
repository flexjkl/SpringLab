package dev.vorstu.controllers;

import dev.vorstu.entity.Student;
import dev.vorstu.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api/base")
public class BaseController {

    private final Service service;

    public BaseController(Service service) {
        this.service = service;
    }

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Student> getStudents() { return service.getAllStudents(); }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Student> getStudentsByGroup(@RequestParam(value = "group") String group) {
        return service.getStudentsByGroup(group);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent) { return service.addStudent(newStudent); }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(Principal user, @PathVariable("id") Long id) {
        Student student = service.getStudentById(user.getName(), id);
        if(student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> changeStudent(Principal user, @RequestBody Student changingStudent) {
        Student student = service.changeStudent(user.getName(), changingStudent);
        if(student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        return service.deleteStudent(id);
    }
}
