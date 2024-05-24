package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.config.UserInfoDetails;
import dev.hieplp.pastebin.auth.service.PasswordService;
import dev.hieplp.pastebin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService implements UserDetailsService {

    private UserService userService;

    private PasswordService passwordService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);
        var password = passwordService.findById(user.getUserId());
        return new UserInfoDetails(user, password);
    }
}
