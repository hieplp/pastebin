package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.payload.request.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.response.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.RegisterResponse;

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
}
