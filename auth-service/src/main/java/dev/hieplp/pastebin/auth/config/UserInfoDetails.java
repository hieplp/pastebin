package dev.hieplp.pastebin.auth.config;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;
import dev.hieplp.pastebin.auth.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserInfoDetails implements UserDetails {

    private final String username;

    private final String password;

    private final UserEntity user;

    public UserInfoDetails(UserEntity user, PasswordEntity password) {
        this.user = user;
        this.username = user.getUsername();
        this.password = new String(password.getPassword());
    }

    public UserInfoDetails(UserEntity user) {
        this.user = user;
        this.username = user.getUsername();
        this.password = null;
    }

    public String userId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
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