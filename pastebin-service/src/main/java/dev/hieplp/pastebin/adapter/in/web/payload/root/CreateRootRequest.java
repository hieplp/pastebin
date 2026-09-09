package dev.hieplp.pastebin.adapter.in.web.payload.root;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRootRequest(
        @NotBlank(message = "Token is required")
        String token,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,50}$", message = "Username must be 3-50 alphanumeric characters, hyphens or underscores")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
        String password
) {
}
