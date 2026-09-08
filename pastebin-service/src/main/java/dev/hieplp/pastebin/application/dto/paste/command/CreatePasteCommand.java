package dev.hieplp.pastebin.application.dto.paste.command;

import dev.hieplp.pastebin.application.dto.file.command.CreateFileCommand;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.Content;
import dev.hieplp.pastebin.domain.vo.Title;

import java.time.Instant;
import java.util.List;

public record CreatePasteCommand(
        Title title,
        Content content,
        Privacy privacy,
        Syntax syntax,
        Alias alias,
        Instant expiredAt,
        boolean burnAfterRead,
        List<CreateFileCommand> files
) {
}
