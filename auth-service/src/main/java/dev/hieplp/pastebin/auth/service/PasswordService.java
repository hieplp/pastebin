package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;

public interface PasswordService {
    /**
     * Generate password entity from raw password
     *
     * @param rawPassword Raw password
     * @return Password entity
     */
    PasswordEntity generatePassword(String rawPassword);

    /**
     * Find password entity by user id
     *
     * @param userId User id
     * @return Password entity
     */
    PasswordEntity findById(String userId);
}
