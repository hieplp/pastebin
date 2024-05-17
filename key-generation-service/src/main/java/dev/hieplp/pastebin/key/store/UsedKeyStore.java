package dev.hieplp.pastebin.key.store;

import dev.hieplp.pastebin.key.entity.UsedKeyEntity;

import java.util.Set;

public interface UsedKeyStore {
    /**
     * Find all key that is used
     *
     * @param key Set of key to find
     * @return Set of used key
     */
    Set<String> findAllByKeyIn(Set<String> key);

    /**
     * Save used key
     *
     * @param key Used key to save
     */
    void save(UsedKeyEntity key);
}
