package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService  extends UserDetailsService {

    public List<User> findAll();

    public Set<Role> findAllRoles();

    public User findOne(int id);

    public void save(User user);

    public void update(int id, User updatedUser);

    public void delete(int id);

    User findByUsername(String username);

    void saveRole(Role role);

    Set<Role> findAllRolesById(List<Integer> roles);


}
