package dev.hieplp.pastebin.application.dto.root.command;

import dev.hieplp.pastebin.domain.vo.Username;

public record CreateRootCommand(
        String token,
        Username username,
        String password
) {
}
