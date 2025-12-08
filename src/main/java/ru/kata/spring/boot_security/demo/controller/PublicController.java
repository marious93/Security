package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.enity.User;
import ru.kata.spring.boot_security.demo.service.DBService;


@Controller

public class PublicController {

    private final DBService dbService;

    public PublicController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "public/create";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "public/create";
        }
        dbService.saveAdmin(user);
        return "redirect:/user";
    }

}
