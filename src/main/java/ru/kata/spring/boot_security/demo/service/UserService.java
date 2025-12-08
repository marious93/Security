package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.enity.Role;
import ru.kata.spring.boot_security.demo.enity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    public Integer findIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void updateUser(int id, User user) {
        User oldUser = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        userRepository.save(oldUser);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

//        Set<String> roles = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            roles.add(role.getName());
//        }

//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(ugh(user.getRoles()))
//                .build();
        // }
        return new org.springframework.security.core.userdetails.User
                (user.getUsername()
                        , user.getPassword()
                        , ugh(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> ugh(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }


}
