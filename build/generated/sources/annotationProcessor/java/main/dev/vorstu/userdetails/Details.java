package dev.vorstu.userdetails;

import dev.vorstu.entity.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class Details extends User {
    private Student userStudent;

    public Details(String username, String password, Collection<? extends GrantedAuthority> authorities, Student userStudent) {
        super(username, password, authorities);
        this.userStudent = userStudent;
    }
}
