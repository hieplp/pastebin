package dev.hieplp.pastebin.application.dto.paste.result;

import dev.hieplp.pastebin.domain.model.Paste;

public record CreatePasteResult(
        String pasteId,
        String alias
) {

    public static CreatePasteResult from(Paste paste) {
        return new CreatePasteResult(
                paste.getPasteId().value(),
                paste.getAlias() != null ? paste.getAlias().value() : null
        );
    }

}
