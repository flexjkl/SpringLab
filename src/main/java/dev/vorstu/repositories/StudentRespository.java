package dev.vorstu.repositories;

import dev.vorstu.dto.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRespository extends CrudRepository<Student, Long> {}
