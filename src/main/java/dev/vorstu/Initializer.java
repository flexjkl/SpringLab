package dev.vorstu;

import dev.vorstu.entity.Password;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    @Autowired
    private StudentRespository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public void initial() {

        Student studentInstance = new Student("fio", "def_group", "+79");

        User student = new User(
                null,
                "student",
                Role.STUDENT,
                new Password("1234"),
                studentInstance,
                true

        );

        User admin = new User(
                null,
                "admin",
                Role.ADMIN,
                new Password("1111"),
                null,
                true
        );

        userRepository.save(admin);
        userRepository.save(student);
    }
}
