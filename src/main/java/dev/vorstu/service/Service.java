package dev.vorstu.service;

import dev.vorstu.dto.StudentDTO;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class Service {
    private final StudentRespository studentRespository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    public Service(StudentRespository studentRespository, UserRepository userRepository, StudentMapper studentMapper) {
        this.studentRespository = studentRespository;
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
    }

    public Iterable<StudentDTO> getStudentsByGroup(String group) {
        return  StreamSupport.stream(studentRespository.findByGroup(group).spliterator(), false)
                .map(studentMapper::toDto)
                .filter(student -> student.getGroup().equals(group))
                .toList();
    }

    public StudentDTO getStudentById(String userName, Long studentId) {
        User requesting = findUserByName(userName);

        if(requesting.getRole() == Role.ADMIN || requesting.getStudent().getId().equals(studentId)) {
            return studentMapper.toDto(requesting.getStudent());
        }

        return null;
    }

    public StudentDTO changeStudent(String userName, Student changingStudent) {
        User requesting = findUserByName(userName);

        if(requesting.getRole() == Role.ADMIN || requesting.getStudent().getId().equals(changingStudent.getId())) {
            return studentMapper.toDto(updateStudent(changingStudent));
        }

        return null;
    }

    public Iterable<StudentDTO> getAllStudents() {
        return StreamSupport.stream(studentRespository.findAll().spliterator(), false)
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Long deleteStudent(Long id) {
        studentRespository.deleteById(id);
        return id;
    }

    public StudentDTO addStudent(Student student) {
        return studentMapper.toDto(studentRespository.save(student));
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
        return userRepository.findByUsername(name);
    }
}
