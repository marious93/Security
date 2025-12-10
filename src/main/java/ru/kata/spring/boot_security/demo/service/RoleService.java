package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.enity.Role;

public interface RoleService {
    public void saveRole(Role role);

    public Role findRoleByName(String name);
}
