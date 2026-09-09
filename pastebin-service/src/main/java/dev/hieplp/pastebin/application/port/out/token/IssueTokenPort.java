package dev.hieplp.pastebin.application.port.out.token;

import dev.hieplp.pastebin.application.dto.token.TokenIssue;
import dev.hieplp.pastebin.domain.enums.Role;
import dev.hieplp.pastebin.domain.enums.TokenType;

public interface IssueTokenPort {

    TokenIssue issue(String subject, Role role, TokenType type);

}
