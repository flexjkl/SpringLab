package dev.vorstu;

import dev.vorstu.entity.Password;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.Student;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer {

    private UserRepository userRepository;

    Initializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initial() {

        List<User> usersList = List.of(
                new User(
                        null,
                        "Alex",
                        Role.STUDENT,
                        new Password("1234"),
                        new Student("Alex May Biba", "IVN-201", "+95678"),
                        true
                ),

                new User(
                        null,
                        "Gregory",
                        Role.STUDENT,
                        new Password("1234"),
                        new Student("Gregory Kay Lind", "IVN-201", "+55634"),
                        true
                ),

                new User(
                        null,
                        "Harry",
                        Role.STUDENT,
                        new Password("1234"),
                        new Student("Harry Net Klein", "ITE-341", "+35623"),
                        true
                ),

                new User(
                        null,
                        "student",
                        Role.STUDENT,
                        new Password("1234"),
                        new Student("fictive", "ITE-341", "+05333"),
                        true
                ),

                new User(
                        null,
                        "admin",
                        Role.ADMIN,
                        new Password("1111"),
                        null,
                        true
                )
        );

        userRepository.saveAll(usersList);
    }
}
