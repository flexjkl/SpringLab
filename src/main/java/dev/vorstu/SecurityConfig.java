package dev.vorstu;

import dev.vorstu.entity.Role;
import dev.vorstu.userdetails.DetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final DetailsService detailsService;

    public SecurityConfig(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/login/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/base/students/{id}").hasAnyAuthority(Role.STUDENT.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/api/base/students").hasAnyAuthority(Role.STUDENT.name(), Role.ADMIN.name())
                .requestMatchers("/api/base/students/**").hasAuthority(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                })
                .and()
                .csrf().disable()
                .cors().disable();

        return http.build();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}


