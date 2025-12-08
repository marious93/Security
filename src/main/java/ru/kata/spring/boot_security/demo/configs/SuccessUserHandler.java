package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static java.util.Arrays.stream;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    private static final String USER = "ROLE_USER";
    private static final String ADMIN = "ROLE_ADMIN";

    private final UserService userService;

    public SuccessUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        int id = userService.findIdByUsername(authentication.getName());

        if (roles.stream().anyMatch(s -> s.contains(ADMIN))) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.stream().anyMatch(s -> s.contains(USER))) {
            httpServletResponse.sendRedirect("/user/" + id);
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}