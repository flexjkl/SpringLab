package dev.vorstu.service;

import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
@Slf4j
public class Service {
    private final StudentRespository studentRespository;
    private final UserRepository userRepository;

    public Service(StudentRespository studentRespository, UserRepository userRepository) {
        this.studentRespository = studentRespository;
        this.userRepository = userRepository;
    }

    public Iterable<Student> getStudentsByGroup(String group) {
        return  StreamSupport.stream(studentRespository.findAll().spliterator(), false)
                .filter(student -> student.getGroup().equals(group))
                .toList();
    }

    public Student getStudentById(String userName, Long studentId) {
        User requesting = findUserByName(userName);

        if(requesting.getRole() == Role.ADMIN) {
            return studentRespository.findById(studentId).orElse(null);
        }

        Long userStudentId = requesting.getStudent().getId();
        if(userStudentId.equals(studentId)) {
            return studentRespository.findById(userStudentId).orElse(null);
        }

        return null;
    }

    public Student changeStudent(String userName, Student changingStudent) {
        User requesting = findUserByName(userName);

        if(requesting.getRole() == Role.ADMIN) {
            return updateStudent(changingStudent);
        }

        if(requesting.getStudent().getId().equals(changingStudent.getId())) {
            return updateStudent(changingStudent);
        }

        return null;
    }

    public Iterable<Student> getAllStudents() {
        return studentRespository.findAll();
    }

    public Long deleteStudent(Long id) {
        studentRespository.deleteById(id);
        return id;
    }

    public Student addStudent(Student student) {
        studentRespository.save(student);
        return student;
    }

    private Student updateStudent(Student student) {
        if(student.getId() == null)
            throw new RuntimeException("id of changing student cannot be null");

        Student changingStudent = studentRespository.findById(student.getId()).orElse(null);

        if(changingStudent != null) {
            changingStudent.setFio(student.getFio());
            changingStudent.setGroup(student.getGroup());
            changingStudent.setPhoneNumber(student.getPhoneNumber());
            studentRespository.save(changingStudent);
        }

        return student;
    }

    private User findUserByName(String name) {
        log.info("USERNAME: " + name);
        return  StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .get();
    }
}
