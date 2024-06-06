package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.payload.request.auth.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.auth.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.response.auth.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.RegisterResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.TokenResponse;

public interface AuthService {
    /**
     * Register a new user
     *
     * @param request Register request
     * @return Register response
     */
    RegisterResponse register(RegisterRequest request);

    /**
     * Login
     *
     * @param request Login request
     * @return Login response
     */
    LoginResponse login(LoginRequest request);


    /**
     * Refresh token
     *
     * @param userId User id
     * @return Token response
     */
    TokenResponse refreshToken(String userId);
}
