package dev.hieplp.pastebin.application.dto.paste.command;

import dev.hieplp.pastebin.application.dto.file.command.CreateFileCommand;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;

import java.util.List;

public record CreatePasteCommand(
        String title,
        String content,
        Privacy privacy,
        Syntax syntax,
        List<CreateFileCommand> files
) {
}
