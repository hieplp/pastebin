package dev.hieplp.pastebin.auth.controller;

import dev.hieplp.pastebin.auth.config.UserInfoDetails;
import dev.hieplp.pastebin.auth.payload.request.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.response.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.RegisterResponse;
import dev.hieplp.pastebin.auth.payload.response.TokenResponse;
import dev.hieplp.pastebin.auth.service.AuthService;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public CommonResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        log.debug("Register with request: {}", request);
        final var response = authService.register(request);
        return CommonResponse.success(response);
    }

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        log.debug("Login with request: {}", request);
        final var response = authService.login(request);
        return CommonResponse.success(response);
    }

    @PostMapping("/refresh")
    public CommonResponse<TokenResponse> refreshToken(@AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Refresh token");
        final var response = authService.refreshToken(userDetails.userId());
        return CommonResponse.success(response);
    }
}
