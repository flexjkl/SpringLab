package dev.vorstu.controllers;

import dev.vorstu.entity.Student;
import dev.vorstu.repositories.StudentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/base")
public class BaseController {

    @Autowired
    StudentRespository studentsRep;

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Student> getStudents() {
        return studentsRep.findAll();
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(Principal user, @PathVariable("id") Long id) {
        return studentsRep.findById(id).orElse(null);
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
    public Student changeStudent(@RequestBody Student changingStudent) {
        return updateStudent(changingStudent);
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
}
