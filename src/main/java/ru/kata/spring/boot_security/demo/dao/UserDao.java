package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserDao {
    public Set<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByUserName(String username);

    public void saveUser(User user);

    public void updateUser(int id, User user);

    public void deleteUser(int id);
}
