package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService  {

    void saveRole(Role role);

    Set<Role> findAllRolesById(int[] roles);

    public Set<Role> findAllRoles();
}
