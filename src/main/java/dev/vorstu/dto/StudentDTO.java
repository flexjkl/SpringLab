package dev.vorstu.dto;

import dev.vorstu.entity.Student;

import java.util.LinkedList;
import java.util.List;

public class StudentDTO {

    private StudentDTO(Student student) {
        id = student.getId();
        fio = student.getFio();
        group = student.getGroup();
        phoneNumber = student.getPhoneNumber();
    }

    public static StudentDTO makeDto(Student student) {
        return new StudentDTO(student);
    }

    public static Iterable<StudentDTO> makeDto(Iterable<Student> list) {
        List<StudentDTO> dtoList = new LinkedList<>();
        for(Student student : list) {
            dtoList.add(new StudentDTO(student));
        }
        return dtoList;
    }

    private Long id = null;

    private String fio = null;

    private String group = null;

    private String phoneNumber = null;
}
