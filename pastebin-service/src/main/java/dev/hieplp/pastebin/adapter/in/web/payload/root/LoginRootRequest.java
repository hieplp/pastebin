package dev.hieplp.pastebin.adapter.in.web.payload.root;

import jakarta.validation.constraints.NotBlank;

public record LoginRootRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
