package dev.vorstu.service;

import dev.vorstu.dto.StudentDTO;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.mappers.StudentMapper;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.userdetails.Details;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public StudentDTO getStudentById(Details requesting, Long studentId) {

        if(requesting.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString())))
            return studentMapper.toDto(studentRespository.findById(studentId).orElse(null));

        if(requesting.getStudentId().equals(studentId)) {
            return studentMapper.toDto(studentRespository.findById(requesting.getStudentId()).orElse(null));
        }

        return null;
    }

    public StudentDTO changeStudent(Details requesting, Student changingStudent) {

        if(changingStudent == null || changingStudent.getId() == null)
            throw new RuntimeException("id of changing student cannot be null");

        if(requesting.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString())) || requesting.getStudentId().equals(changingStudent.getId()))
            return studentMapper.toDto(studentRespository.save(changingStudent));

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

}
