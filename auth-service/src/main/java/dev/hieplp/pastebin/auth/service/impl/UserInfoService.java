package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;
import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.service.PasswordService;
import dev.hieplp.pastebin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    static class UserInfoDetails implements UserDetails {

        private final String username;
        private final String password;

        private UserInfoDetails(UserEntity user, PasswordEntity password) {
            this.username = user.getUsername();
            this.password = new String(password.getPassword());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
