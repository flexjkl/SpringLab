package dev.vorstu;

import dev.vorstu.dto.Student;
import dev.vorstu.repositories.StudentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    @Autowired
    private StudentRespository studentRepository;

    public void initial() {
        studentRepository.save(new Student("fio", "def_group", "+79"));
    }
}
