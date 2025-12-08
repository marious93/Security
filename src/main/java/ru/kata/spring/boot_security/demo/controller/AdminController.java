package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.enity.User;
import ru.kata.spring.boot_security.demo.service.DBService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DBService dbService;
    private final UserService userService;

    public AdminController(DBService dbService, UserService userService) {
        this.dbService = dbService;
        this.userService = userService;
    }
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        dbService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable int id,Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/info";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "admin/users";
    }

    @GetMapping("/edit/{id}")
    public String updateUser(@PathVariable int id,Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser1(@ModelAttribute("user")@Validated User user, BindingResult bindingResult,
                              @PathVariable int id, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

}
