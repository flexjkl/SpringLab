package dev.vorstu.dto;

import dev.vorstu.entity.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Setter @Getter
public class StudentDTO {

    private StudentDTO(Student student) {
        id = student.getId();
        fio = student.getFio();
        group = student.getGroup();
        phoneNumber = student.getPhoneNumber();
    }

    public static StudentDTO makeDto(Student student) {
        return student != null ? new StudentDTO(student) : null;
    }

    public static Iterable<StudentDTO> makeDto(Iterable<Student> list) {
        if(list == null) return Collections.emptyList();

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
