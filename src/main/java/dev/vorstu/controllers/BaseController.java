package dev.vorstu.controllers;

import dev.vorstu.dto.StudentDTO;
import dev.vorstu.entity.Student;
import dev.vorstu.service.Service;
import dev.vorstu.userdetails.Details;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Iterable<StudentDTO> getStudents() { return service.getAllStudents(); }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<StudentDTO> getStudentsByGroup(@RequestParam(value = "group") String group) {
        return service.getStudentsByGroup(group);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO createStudent(@RequestBody Student newStudent) { return service.addStudent(newStudent); }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDTO> getStudentById(@AuthenticationPrincipal Details user, @PathVariable("id") Long id) {
        StudentDTO student = service.getStudentById(user, id);
        if(student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDTO> changeStudent(@AuthenticationPrincipal Details user, @RequestBody Student changingStudent) {
        StudentDTO student = service.changeStudent(user, changingStudent);
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
