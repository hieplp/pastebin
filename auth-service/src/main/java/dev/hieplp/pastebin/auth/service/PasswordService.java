package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.payload.response.user.PasswordResponse;

public interface PasswordService {
    /**
     * Call user-service to find password by user id
     *
     * @param userId id of user
     * @return Password response
     */
    PasswordResponse findByUserId(String userId);
}
