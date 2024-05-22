package dev.hieplp.pastebin.auth.store;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;

public interface PasswordStore {
    PasswordEntity findById(String id);
}
