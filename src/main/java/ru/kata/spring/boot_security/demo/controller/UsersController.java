package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersController {

    private UserServiceImp userServiceImp;

    @Autowired
    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/")
    public String gotoUsers() {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> listOfUsers = userServiceImp.findAll();
        model.addAttribute("listOfUsers", listOfUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String addNewUserGet(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping("/users/new")
    public String addNewUserPost(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "newUser";
        userServiceImp.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        User user = userServiceImp.findOne(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PatchMapping("/users/{id}")
    public String updateUserPost(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "editUser";
        userServiceImp.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/users";
    }

}
