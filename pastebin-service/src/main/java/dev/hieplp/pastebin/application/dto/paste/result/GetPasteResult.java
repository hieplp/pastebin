package dev.hieplp.pastebin.application.dto.paste.result;

import dev.hieplp.pastebin.application.dto.file.result.GetFileResult;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.model.Paste;

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

    public static GetPasteResult from(Paste paste, List<GetFileResult> files) {
        return new GetPasteResult(
                paste.getPasteId().value(),
                paste.getTitle() != null ? paste.getTitle().value() : null,
                paste.getAlias() != null ? paste.getAlias().value() : null,
                paste.getContent() != null ? paste.getContent().value() : null,
                paste.getPrivacy(),
                paste.getSyntax(),
                paste.getCreatedAt(),
                paste.getExpiredAt(),
                files
        );
    }

}
