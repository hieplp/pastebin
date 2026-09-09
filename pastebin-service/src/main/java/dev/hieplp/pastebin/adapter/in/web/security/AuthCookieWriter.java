package dev.hieplp.pastebin.adapter.in.web.security;

import dev.hieplp.pastebin.adapter.out.security.config.JwtProperties;
import dev.hieplp.pastebin.application.dto.auth.LoginResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthCookieWriter {

    private final JwtProperties props;

    public void write(HttpServletResponse response, LoginResult tokens) {
        add(response, props.accessCookieName(), tokens.accessToken(), tokens.accessTtl().getSeconds());
        add(response, props.refreshCookieName(), tokens.refreshToken(), tokens.refreshTtl().getSeconds());
    }

    public String readRefresh(HttpServletRequest request) {
        return read(request, props.refreshCookieName());
    }

    public String readAccess(HttpServletRequest request) {
        return read(request, props.accessCookieName());
    }

    private void add(HttpServletResponse response, String name, String value, long maxAgeSeconds) {
        var cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(props.cookieSecure())
                .sameSite(props.cookieSameSite())
                .path(props.cookiePath())
                .maxAge(maxAgeSeconds)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private static String read(HttpServletRequest request, String name) {
        var cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
