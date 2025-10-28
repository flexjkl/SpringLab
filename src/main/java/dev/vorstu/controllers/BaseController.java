package dev.vorstu.controllers;

import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.StreamSupport;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/base")
public class BaseController {

    @Autowired
    StudentRespository studentsRep;

    @Autowired
    UserRepository userRep;

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Student> getStudents() { return studentsRep.findAll(); }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(Principal user, @PathVariable("id") Long id) {
        User requesting = findUserByName(user.getName());
        if(requesting.getStudent().getId().equals(id) || requesting.getRole() == Role.ADMIN) {
            return new ResponseEntity<>(requesting.getStudent(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Student> getStudentsByGroup(@RequestParam(value = "group") String group) {
        return StreamSupport.stream(studentsRep.findAll().spliterator(), false)
                .filter(student -> student.getGroup().equals(group))
                .toList();
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent) { return addStudent(newStudent); }

    @PutMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> changeStudent(Principal user, @RequestBody Student changingStudent) {
        User requesting = findUserByName(user.getName());
        if(requesting.getStudent().getId().equals(changingStudent.getId()) || requesting.getRole() == Role.ADMIN) {
            return new ResponseEntity<>(updateStudent(changingStudent), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        studentsRep.deleteById(id);
        return id;
    }

    private Student addStudent(Student student) {
        studentsRep.save(student);
        return student;
    }

    private Student updateStudent(Student student) {
        if(student.getId() == null)
            throw new RuntimeException("id of changing student cannot be null");

        Student changingStudent = studentsRep.findById(student.getId()).orElse(null);

        if(changingStudent != null) {
            changingStudent.setFio(student.getFio());
            changingStudent.setGroup(student.getGroup());
            changingStudent.setPhoneNumber(student.getPhoneNumber());
            studentsRep.save(changingStudent);
        }

        return student;
    }

    private User findUserByName(String name) {
        return  StreamSupport.stream(userRep.findAll().spliterator(), false)
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .get();
    }
}
