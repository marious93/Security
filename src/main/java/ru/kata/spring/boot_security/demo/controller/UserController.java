package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.enity.User;
import ru.kata.spring.boot_security.demo.service.DBService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final DBService dbService;

    public UserController(UserService userService, DBService dbService) {
        this.userService = userService;
        this.dbService = dbService;
    }

    @GetMapping("/{id}")
    public String info(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "private/info";
    }


}
