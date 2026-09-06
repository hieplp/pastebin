package dev.hieplp.pastebin.application.dto.paste.command;

import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;

public record CreatePasteCommand(
        String title,
        String content,
        Privacy privacy,
        Syntax syntax
) {
}
