package ru.kata.spring.boot_security.demo.controller;

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

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
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
                              @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
