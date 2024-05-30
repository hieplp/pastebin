package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.request.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.response.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.RegisterResponse;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
import dev.hieplp.pastebin.auth.payload.response.UserResponse;
import dev.hieplp.pastebin.auth.service.AuthService;
import dev.hieplp.pastebin.auth.service.PasswordService;
import dev.hieplp.pastebin.auth.service.TokenService;
import dev.hieplp.pastebin.auth.service.UserService;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import dev.hieplp.pastebin.common.exception.DuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordService passwordService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.info("Register with request: {}", request);

        // Check if username is already taken
        if (userService.existsByUsername(request.username())) {
            log.warn("Username: {} is already taken", request.username());
            throw new DuplicateException("Username is already taken");
        }

        // Password
        var password = passwordService.generatePassword(request.password());

        // User
        var user = UserEntity.builder()
                .username(request.username())
                .name(request.name())
                .build();

        // Bidirectional
        user.setPassword(password);
        password.setUser(user);

        // Save
        user = userService.save(user);

        return new RegisterResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login with request: {}", request);

        // Validate username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        //
        var user = userService.findByUsername(request.username());

        var accessToken = tokenService.generate(TokenType.ACCESS, user);

        var refreshToken = tokenService.generate(TokenType.REFRESH, user);

        return LoginResponse.builder()
                .user(new UserResponse(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refreshToken(String userId) {
        log.info("Refresh token");
        var user = userService.findByUserId(userId);
        return tokenService.generate(TokenType.ACCESS, user);
    }
}
