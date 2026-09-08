package dev.hieplp.pastebin.adapter.in.web.payload.paste;

import dev.hieplp.pastebin.adapter.in.web.payload.file.FileResponse;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;

import java.time.Instant;
import java.util.List;

public record PasteResponse(
        String pasteId,
        String title,
        String alias,
        String content,
        Privacy privacy,
        Syntax syntax,
        Instant createdAt,
        Instant expiredAt,
        List<FileResponse> files
) {
}
