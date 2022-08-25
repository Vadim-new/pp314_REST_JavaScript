package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleDao {
    void saveRole(Role role);

    Set<Role> findAllRolesById(int[] roles);

    public Role findRoleById(int role);

    public Set<Role> findAllRoles();
}
