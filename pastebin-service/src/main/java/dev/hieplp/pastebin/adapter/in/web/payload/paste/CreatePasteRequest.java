package dev.hieplp.pastebin.adapter.in.web.payload.paste;

import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreatePasteRequest(
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        String content,

        Privacy privacy,

        Syntax syntax,

        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,50}$", message = "Alias must be 3-50 alphanumeric characters, hyphens or underscores")
        String alias,

        Instant expiredAt,

        boolean burnAfterRead
) {
}
