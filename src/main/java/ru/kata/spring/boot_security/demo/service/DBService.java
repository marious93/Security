package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.enity.Role;
import ru.kata.spring.boot_security.demo.enity.User;

@Service
@Transactional
public class DBService {
    private final RoleService roleService;
    private final UserService userService;
    private static final String USER_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    public DBService(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public void saveUser(User user) {
        Role role = roleService.findByName(USER_ROLE);
        if (role == null) {
            role = new Role(USER_ROLE);
        }
        roleService.save(role);
        user.addRole(role);
        userService.save(user);
    }

    public void saveAdmin(User user) {
        Role adminRole = roleService.findByName(ADMIN_ROLE);
        if (adminRole == null) {
            adminRole = new Role(ADMIN_ROLE);
        }
        roleService.save(adminRole);
        user.addRole(adminRole);
        userService.save(user);
    }

    public void addRole(User user, Role newRole) {
        Role role = roleService.findByName(newRole.getName());
        if (role == null) {
            role = new Role(newRole.getName());
            roleService.save(role);
        }
        user.addRole(role);
        userService.save(user);
    }

}
