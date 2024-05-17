package dev.hieplp.pastebin.key.service;

import java.util.Set;

public interface UsedKeyService {
    /**
     * Find all used key
     *
     * @param key Set of key to find
     * @return Set of used key
     */
    Set<String> findAllByKeyIn(Set<String> key);

    /**
     * Save used key
     *
     * @param key Key to save
     */
    void save(String key);
}
