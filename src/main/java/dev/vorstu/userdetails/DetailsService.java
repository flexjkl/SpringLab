package dev.vorstu.userdetails;

import dev.vorstu.entity.User;
import dev.vorstu.repositories.StudentRespository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException(username);

        return new Details(
                user.getUsername(),
                user.getPassword().toString(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())),
                user.getStudent().getId()
        );
    }
}
