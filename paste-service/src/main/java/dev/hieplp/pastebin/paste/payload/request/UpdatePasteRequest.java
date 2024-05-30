package dev.hieplp.pastebin.paste.payload.request;

import dev.hieplp.pastebin.common.enums.paste.PasteStatus;

public record UpdatePasteRequest(
        String alias,
        String description,
        String content,
        PasteStatus status
) {
}
