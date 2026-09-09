package dev.hieplp.pastebin.application.port.in.auth;

import dev.hieplp.pastebin.application.dto.auth.LoginResult;

public interface RefreshTokenUseCase {

    LoginResult refresh(String refreshToken);

}
