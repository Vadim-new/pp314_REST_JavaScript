package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleService roleService) {
        this.userService = userServiceImpl;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String addNewUserGet(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.findAllRoles());
        return "newUser";
    }

    @PostMapping("/new")
    public String addNewUserPost(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 @RequestParam("roles") int[] roles) {
        if (bindingResult.hasErrors()) return "newUser";
        user.setRoles(roleService.findAllRolesById(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String updateUserPatch(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult, @PathVariable("id") int id,
                                 @RequestParam("roles") int[] roles) {
        if (bindingResult.hasErrors()) return "editUser";
        user.setRoles(roleService.findAllRolesById(roles));
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
