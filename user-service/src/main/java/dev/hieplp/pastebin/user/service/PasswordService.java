package dev.hieplp.pastebin.user.service;


import dev.hieplp.pastebin.user.entity.PasswordEntity;
import dev.hieplp.pastebin.user.payload.response.PasswordResponse;

public interface PasswordService {
    /**
     * Generate password entity from raw password
     *
     * @param rawPassword Raw password
     * @return Password entity
     */
    PasswordEntity generatePassword(String rawPassword);

    /**
     * Find password response by user id
     *
     * @param userId User id
     * @return Password response
     */
    PasswordResponse findByUserId(String userId);
}
