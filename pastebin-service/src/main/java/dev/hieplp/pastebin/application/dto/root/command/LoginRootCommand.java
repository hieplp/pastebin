package dev.hieplp.pastebin.application.dto.root.command;

import dev.hieplp.pastebin.domain.vo.Username;

public record LoginRootCommand(
        Username username,
        String password
) {
}
