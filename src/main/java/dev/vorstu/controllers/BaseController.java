package dev.vorstu.controllers;

import dev.vorstu.dto.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@RestController
@RequestMapping("api/base")
public class BaseController {

    private Long counter = 0L;

    private final List<Student> students = new ArrayList<>();

    private Long generateId() { return counter++; }

    @PostConstruct
    private void init() {
        Collections.addAll(students,
                new Student(1L, "User1", "VM", "+7"),
                new Student(2L,"User2", "VM", "+8"),
                new Student(3L,"User3", "AM", "+99")
        );
    }

    @GetMapping("students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable("id") Long id) {
        return students.stream()
                .filter(el -> el.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("student with id: " + id + "was not found"));
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@RequestPart(value = "group") String group) {
        return students.stream()
                .filter(el -> el.getGroup().equals(group))
                .findFirst()
                .orElse(null);
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent) { return addStudent(newStudent); }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent) {
        return updateStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        return removeStudent(id);
    }

    private Student addStudent(Student student) {
        student.setId(generateId());
        students.add(student);
        return student;
    }

    private Student updateStudent(Student student) {
        if(student.getId() == null)
            throw new RuntimeException("id of changing student cannot be null");

        Student changingStudent = students.stream()
                .filter(el -> Objects.equals(el.getId(), student.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("student with id: " + student.getId() + " was not found"));

        changingStudent.setFio(student.getFio());
        changingStudent.setGroup(student.getGroup());
        changingStudent.setPhoneNumber(student.getPhoneNumber());
        return student;
    }

    private Long removeStudent(Long id) {
        students.removeIf(el -> el.getId().equals(id));
        return id;
    }
}
