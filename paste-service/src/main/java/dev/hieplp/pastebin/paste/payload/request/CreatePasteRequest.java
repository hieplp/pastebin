package dev.hieplp.pastebin.paste.payload.request;

import dev.hieplp.pastebin.common.enums.paste.PastePrivacy;

import java.sql.Timestamp;

public record CreatePasteRequest(
        String alias,
        String description,
        String content,
        PastePrivacy privacy,
        Timestamp expiredAt
) {
}
