package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.enity.Role;
import ru.kata.spring.boot_security.demo.enity.User;
import ru.kata.spring.boot_security.demo.service.DBService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller

public class PublicController {

    private final UserService userService;
    private final RoleService roleService;
    private final DBService dbService;

    public PublicController(UserService userService, RoleService roleService, DBService dbService) {
        this.userService = userService;
        this.roleService = roleService;
        this.dbService = dbService;
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "public/create";
    }

    @PostMapping("/new")
    @Transactional
    public String createUser(@ModelAttribute("user") User user) {
        //BindingResult bindingResult)
//        if (bindingResult.hasErrors()) {
//            return "public/create";
//        }
        //user.setRole("USER");
        dbService.saveAdmin(user);
        return "redirect:/user";
    }


}
