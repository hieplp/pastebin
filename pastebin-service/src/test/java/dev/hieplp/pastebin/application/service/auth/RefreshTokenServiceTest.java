package dev.hieplp.pastebin.application.service.auth;

import dev.hieplp.pastebin.application.dto.token.TokenClaims;
import dev.hieplp.pastebin.application.dto.token.TokenIssue;
import dev.hieplp.pastebin.application.port.out.token.IssueTokenPort;
import dev.hieplp.pastebin.application.port.out.token.ParseTokenPort;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private ParseTokenPort parseTokenPort;

    @Mock
    private IssueTokenPort issueTokenPort;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void refresh_withAccessToken_throwsUnauthorized() {
        when(parseTokenPort.parse("access")).thenReturn(Optional.of(
                new TokenClaims("root-1", Role.ROOT, TokenType.ACCESS)
        ));

        var ex = assertThrows(UnauthorizedException.class, () -> refreshTokenService.refresh("access"));
        assertEquals("Invalid refresh token", ex.getMessage());
    }

    @Test
    void refresh_withRefreshToken_issuesNewPair() {
        when(parseTokenPort.parse("refresh")).thenReturn(Optional.of(
                new TokenClaims("root-1", Role.ROOT, TokenType.REFRESH)
        ));
        when(issueTokenPort.issue("root-1", Role.ROOT, TokenType.ACCESS))
                .thenReturn(new TokenIssue("new-access", Duration.ofMinutes(15)));
        when(issueTokenPort.issue("root-1", Role.ROOT, TokenType.REFRESH))
                .thenReturn(new TokenIssue("new-refresh", Duration.ofDays(7)));

        var result = refreshTokenService.refresh("refresh");

        assertEquals("new-access", result.accessToken());
        assertEquals("new-refresh", result.refreshToken());
    }

}
