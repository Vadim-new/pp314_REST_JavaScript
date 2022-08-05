package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userService = userServiceImp;
    }

    @GetMapping()
    public String getAllUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String addNewUserGet(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", userService.findAllRoles());
        return "newUser";
    }

    @PostMapping("/new")
    public String addNewUserPost(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 @RequestParam("roles") ArrayList<Integer> roles) {
        if (bindingResult.hasErrors()) return "newUser";
        user.setRoles(userService.findAllRolesById(roles));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String updateUserPost(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "editUser";
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
