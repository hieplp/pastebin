package dev.hieplp.pastebin.auth.payload.response.auth;

import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private UserResponse user;
    private TokenResponse accessToken;
    private TokenResponse refreshToken;
}
