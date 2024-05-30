package dev.hieplp.pastebin.paste.payload.request;

import dev.hieplp.pastebin.common.enums.paste.PasteStatus;

public record CreatePasteRequest(
        String alias,
        String description,
        String content,
        PasteStatus status
) {
}
