package dev.hieplp.pastebin.application.service.auth;

import dev.hieplp.pastebin.application.dto.auth.LoginResult;
import dev.hieplp.pastebin.application.port.in.auth.RefreshTokenUseCase;
import dev.hieplp.pastebin.application.port.out.token.IssueTokenPort;
import dev.hieplp.pastebin.application.port.out.token.ParseTokenPort;
import dev.hieplp.pastebin.domain.enums.TokenType;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {

    private final ParseTokenPort parseTokenPort;
    private final IssueTokenPort issueTokenPort;

    @Override
    public LoginResult refresh(String refreshToken) {
        log.info("Refresh token");

        var claims = parseTokenPort.parse(refreshToken)
                .filter(c -> c.type() == TokenType.REFRESH)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        var access = issueTokenPort.issue(claims.subject(), claims.role(), TokenType.ACCESS);
        var refresh = issueTokenPort.issue(claims.subject(), claims.role(), TokenType.REFRESH);
        log.info("Token refreshed subject={} role={}", claims.subject(), claims.role());

        return new LoginResult(
                claims.subject(),
                access.token(),
                access.ttl(),
                refresh.token(),
                refresh.ttl()
        );
    }

}
