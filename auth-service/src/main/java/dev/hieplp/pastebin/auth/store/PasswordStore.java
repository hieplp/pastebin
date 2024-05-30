package dev.hieplp.pastebin.auth.store;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;

public interface PasswordStore {
    /**
     * Find password entity by user id
     *
     * @param id User id
     * @return Password entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException If password entity not found
     */
    PasswordEntity findById(String id);
}
