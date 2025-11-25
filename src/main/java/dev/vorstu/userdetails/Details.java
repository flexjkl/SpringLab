package dev.vorstu.userdetails;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter @Setter
public class Details extends User {

    private Long studentId;

    public Details(String username, String password, Collection<? extends GrantedAuthority> authorities, Long studentId) {
        super(username, password, authorities);
        this.studentId = studentId;
    }
}
