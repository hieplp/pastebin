package dev.hieplp.pastebin.adapter.in.web.security;

import dev.hieplp.pastebin.application.port.out.token.ParseTokenPort;
import dev.hieplp.pastebin.domain.enums.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthCookieWriter authCookieWriter;
    private final ParseTokenPort parseTokenPort;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var token = authCookieWriter.readAccess(request);
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            parseTokenPort.parse(token)
                    .filter(claims -> claims.type() == TokenType.ACCESS)
                    .ifPresent(claims -> {
                        var auth = new UsernamePasswordAuthenticationToken(
                                claims.subject(),
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + claims.role().name()))
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
        }
        filterChain.doFilter(request, response);
    }

}
