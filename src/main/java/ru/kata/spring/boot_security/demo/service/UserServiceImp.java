package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.security.Encoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final Encoder encoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, Encoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User findOne(int id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(encoder.passwordCoder(user));
    }

    @Override
    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Set<Role> findAllRolesById(List<Integer> roles) {
        return new HashSet<>(roleRepository.findAllById(roles));
    }

    @Override
    public Set<Role> findAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }

}