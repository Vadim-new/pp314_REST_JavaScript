package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitUsers {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public InitUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsers() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        User admin = new User("Ivan", "Petrov", 35, "+7-495-456-98-87", "admin@email.ru", "admin", "admin");
        admin.addRole(adminRole);

        User user = new User("Sergey", "Sidorov", 28, "+7-496-777-23-11", "user@email.ru", "user", "user");
        user.addRole(userRole);

        userService.saveUser(admin);
        userService.saveUser(user);

    }
}
