package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users_table")
public class User implements UserDetails {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия должна быть от 2 до 30 символов")
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Min(value = 0, message = "Возраст не может быть меньше 0")
    @Column(name = "age", nullable = false)
    private int age;

    @NotEmpty(message = "Номер телефона не может быть пустой")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "Email не может быть пустой")
    @Email(message = "Email указан не верно")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String surname, int age, String phoneNumber, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && name.equals(user.name) && surname.equals(user.surname) && phoneNumber.equals(user.phoneNumber) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", age=" + age + ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'' + '}';
    }
}
