package dev.vorstu.dto;

import dev.vorstu.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class StudentDTO {

    public StudentDTO() {}

    private Long id = null;

    private String fio = null;

    private String group = null;

    private String phoneNumber = null;
}
