package dev.vorstu.dto;

import dev.vorstu.entity.Student;

public class StudentDTO {

    public StudentDTO(Student student) {
        id = student.getId();
        fio = student.getFio();
        group = student.getGroup();
        phoneNumber = student.getPhoneNumber();
    }

    private Long id = null;

    private String fio = null;

    private String group = null;

    private String phoneNumber = null;
}
