package dev.hieplp.pastebin.paste.payload.request;

import java.sql.Timestamp;

public record CreatePasteRequest(
        String alias,
        String title,
        String content,
        String privacy,
        Timestamp expiredAt
) {
}
