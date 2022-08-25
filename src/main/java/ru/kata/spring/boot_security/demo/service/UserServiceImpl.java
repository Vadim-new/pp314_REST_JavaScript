package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.exception_handling.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Set<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        User getUser = userDao.getUserById(id);
        if (getUser == null) {
            throw new UserNotFoundException();
        } else {
            return getUser;
        }
    }

    @Override
    public User getUserByUserName(String username) {
        User getUser = userDao.getUserByUserName(username);
        if (getUser == null) {
            throw new UserNotFoundException();
        } else {
            return getUser;
        }
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user) {

        User getUser = userDao.getUserById(id);
        if (getUser == null) {
            throw new UserNotFoundException();
        } else {
            userDao.updateUser(id, user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User getUser = userDao.getUserById(id);
        if (getUser == null) {
            throw new UserNotFoundException();
        } else {
            userDao.deleteUser(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}