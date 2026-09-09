package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.security.AuthCookieWriter;
import dev.hieplp.pastebin.application.port.in.auth.RefreshTokenUseCase;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import dev.hieplp.pastebin.domain.util.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCookieWriter authCookieWriter;

    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/refresh")
    public BaseResponse<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = Strings.trim(authCookieWriter.readRefresh(request))
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        authCookieWriter.write(response, refreshTokenUseCase.refresh(refreshToken));
        return BaseResponse.ok(null);
    }

}
