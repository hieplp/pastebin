package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.LoginRootCommand;
import dev.hieplp.pastebin.application.dto.auth.LoginResult;
import dev.hieplp.pastebin.application.port.in.root.LoginRootUseCase;
import dev.hieplp.pastebin.application.port.out.password.MatchPasswordPort;
import dev.hieplp.pastebin.application.port.out.root.GetRootPort;
import dev.hieplp.pastebin.application.port.out.token.IssueTokenPort;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginRootService implements LoginRootUseCase {

    private final GetRootPort getRootPort;

    private final MatchPasswordPort matchPasswordPort;

    private final IssueTokenPort issueTokenPort;

    @Override
    public LoginResult login(CommandEnvelope<LoginRootCommand> envelope) {
        var command = envelope.command();
        log.info("Login root with username={}", command.username());

        var root = getRootPort.findByUsername(command.username())
                .orElseThrow(LoginRootService::invalidCredentials);

        if (!matchPasswordPort.matches(command.password(), root.getPasswordHash())) {
            throw invalidCredentials();
        }

        var access = issueTokenPort.issue(root.getRootId().value(), Role.ROOT, TokenType.ACCESS);
        var refresh = issueTokenPort.issue(root.getRootId().value(), Role.ROOT, TokenType.REFRESH);
        log.info("Root logged in rootId={}", root.getRootId());

        return new LoginResult(
                root.getUsername().value(),
                access.token(),
                access.ttl(),
                refresh.token(),
                refresh.ttl()
        );
    }

    private static UnauthorizedException invalidCredentials() {
        return new UnauthorizedException("Invalid credentials");
    }

}
