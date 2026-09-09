package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.LoginRootCommand;
import dev.hieplp.pastebin.application.dto.token.TokenIssue;
import dev.hieplp.pastebin.application.port.out.password.MatchPasswordPort;
import dev.hieplp.pastebin.application.port.out.root.GetRootPort;
import dev.hieplp.pastebin.application.port.out.token.IssueTokenPort;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import dev.hieplp.pastebin.domain.model.Root;
import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.Username;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginRootServiceTest {

    @Mock
    private GetRootPort getRootPort;

    @Mock
    private MatchPasswordPort matchPasswordPort;

    @Mock
    private IssueTokenPort issueTokenPort;

    @InjectMocks
    private LoginRootService loginRootService;

    @Test
    void login_unknownUser_throwsUnauthorized() {
        when(getRootPort.findByUsername(Username.of("admin"))).thenReturn(Optional.empty());

        var ex = assertThrows(UnauthorizedException.class, () -> loginRootService.login(envelope()));
        assertEquals("Invalid credentials", ex.getMessage());
        verifyNoInteractions(issueTokenPort);
    }

    @Test
    void login_wrongPassword_throwsUnauthorized() {
        var root = Root.create(Username.of("admin"), "hashed", Actor.system());
        when(getRootPort.findByUsername(Username.of("admin"))).thenReturn(Optional.of(root));
        when(matchPasswordPort.matches("password1", "hashed")).thenReturn(false);

        var ex = assertThrows(UnauthorizedException.class, () -> loginRootService.login(envelope()));
        assertEquals("Invalid credentials", ex.getMessage());
        verifyNoInteractions(issueTokenPort);
    }

    @Test
    void login_validCredentials_issuesAccessAndRefresh() {
        var root = Root.create(Username.of("admin"), "hashed", Actor.system());
        when(getRootPort.findByUsername(Username.of("admin"))).thenReturn(Optional.of(root));
        when(matchPasswordPort.matches("password1", "hashed")).thenReturn(true);
        when(issueTokenPort.issue(root.getRootId().value(), Role.ROOT, TokenType.ACCESS))
                .thenReturn(new TokenIssue("access", Duration.ofMinutes(15)));
        when(issueTokenPort.issue(root.getRootId().value(), Role.ROOT, TokenType.REFRESH))
                .thenReturn(new TokenIssue("refresh", Duration.ofDays(7)));

        var result = loginRootService.login(envelope());

        assertEquals("admin", result.username());
        assertEquals("access", result.accessToken());
        assertEquals("refresh", result.refreshToken());
    }

    private static CommandEnvelope<LoginRootCommand> envelope() {
        return CommandEnvelope.anonymous(new LoginRootCommand(Username.of("admin"), "password1"));
    }

}
