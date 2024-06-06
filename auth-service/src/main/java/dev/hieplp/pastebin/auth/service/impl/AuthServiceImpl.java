package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.payload.request.auth.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.auth.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.request.user.CreateUserRequest;
import dev.hieplp.pastebin.auth.payload.response.auth.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.RegisterResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.TokenResponse;
import dev.hieplp.pastebin.auth.service.AuthService;
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

        // Save user
        var user = userService.create(CreateUserRequest.builder()
                .username(request.username())
                .name(request.name())
                .password(request.password())
                .build());

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
                .user(user)
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
