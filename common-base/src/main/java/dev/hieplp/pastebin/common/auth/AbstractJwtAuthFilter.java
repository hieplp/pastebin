package dev.hieplp.pastebin.common.auth;

import dev.hieplp.pastebin.common.enums.token.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public abstract class AbstractJwtAuthFilter extends OncePerRequestFilter {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get token from header
            final var jwt = getTokenFromHeader(request);

            // Request does not contain token. Let Spring Security handle it.
            if (ObjectUtils.isEmpty(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Determine token type
            var tokenType = request.getServletPath().contains("/auth/refresh")
                    ? TokenType.REFRESH
                    : TokenType.ACCESS;

            // Validate token
            var userDetails = TokenUtil.validate(secretKey, tokenType, jwt);

            // Set authentication context
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.debug("Error occurred while processing JWT token: {}", e.getMessage());
        } finally {
            // Continue filter chain
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
