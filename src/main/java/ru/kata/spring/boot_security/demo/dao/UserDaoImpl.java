package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.Encoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;
    private Encoder encoder;

    @Autowired
    public UserDaoImpl(Encoder encoder) {
        this.encoder = encoder;
    }


    @Override
    public Set<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUserName(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.userName =: username", User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public void saveUser(User user) {
        encoder.passwordCoder(user);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(int id, User user) {
        user.setId(id);
        encoder.passwordCoder(user);
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUserById(id));
    }
}
