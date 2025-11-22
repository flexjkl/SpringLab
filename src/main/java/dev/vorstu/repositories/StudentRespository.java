package dev.vorstu.repositories;

import dev.vorstu.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRespository extends CrudRepository<Student, Long> {

    Iterable<Student> findByGroup(String group);

}
