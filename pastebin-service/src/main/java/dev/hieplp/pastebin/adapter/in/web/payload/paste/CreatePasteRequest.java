package dev.hieplp.pastebin.adapter.in.web.payload.paste;

import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;

public record CreatePasteRequest(
        String title,
        String content,
        Privacy privacy,
        Syntax syntax
) {
}
