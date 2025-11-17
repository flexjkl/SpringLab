package dev.vorstu.mappers;

import dev.vorstu.dto.StudentDTO;
import dev.vorstu.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);
}
