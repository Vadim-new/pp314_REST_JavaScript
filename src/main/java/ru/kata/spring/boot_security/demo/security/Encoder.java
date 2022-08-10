package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

@Component
public class Encoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return user;
    }

}
