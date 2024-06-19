package dev.hieplp.pastebin.auth.controller;

import dev.hieplp.pastebin.auth.payload.request.auth.LoginRequest;
import dev.hieplp.pastebin.auth.payload.request.auth.RegisterRequest;
import dev.hieplp.pastebin.auth.payload.response.auth.LoginResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.RegisterResponse;
import dev.hieplp.pastebin.auth.payload.response.auth.TokenResponse;
import dev.hieplp.pastebin.auth.service.AuthService;
import dev.hieplp.pastebin.common.auth.UserInfoDetails;
import dev.hieplp.pastebin.common.enums.token.TokenType;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public CommonResponse<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse) {
        log.debug("Login with request: {}", request);

        final var response = authService.login(request);

        httpResponse.addCookie(createCookie(TokenType.REFRESH.getValue(), response.getRefreshToken()));
        httpResponse.addCookie(createCookie(TokenType.ACCESS.getValue(), response.getAccessToken()));

        return CommonResponse.success(response);
    }

    @DeleteMapping("/logout")
    public CommonResponse<?> logout(HttpServletResponse httpResponse) {
        log.debug("Logout");
        httpResponse.addCookie(deleteCookie(TokenType.REFRESH.getValue()));
        httpResponse.addCookie(deleteCookie(TokenType.ACCESS.getValue()));
        return CommonResponse.success(null);
    }

    @PostMapping("/refresh")
    public CommonResponse<TokenResponse> refreshToken(@AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Refresh token");
        final var response = authService.refreshToken(userDetails.getUserId());
        return CommonResponse.success(response);
    }

    private Cookie createCookie(String name, TokenResponse token) {
        final var cookie = new Cookie(name, token.token());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(Math.toIntExact((token.expiredAt().getTime() - System.currentTimeMillis()) / 1000));
        return cookie;
    }

    private Cookie deleteCookie(String name) {
        final var cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        return cookie;
    }

}
