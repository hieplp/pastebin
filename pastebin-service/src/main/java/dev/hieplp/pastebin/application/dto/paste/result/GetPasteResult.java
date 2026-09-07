package dev.hieplp.pastebin.application.dto.paste.result;

import dev.hieplp.pastebin.application.dto.file.result.GetFileResult;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;

import java.time.Instant;
import java.util.List;

public record GetPasteResult(
        String pasteId,
        String title,
        String alias,
        String content,
        Privacy privacy,
        Syntax syntax,
        Instant createdAt,
        Instant expiredAt,
        List<GetFileResult> files
) {
}
