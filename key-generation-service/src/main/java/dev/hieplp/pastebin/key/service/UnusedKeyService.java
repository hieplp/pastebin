package dev.hieplp.pastebin.key.service;

public interface UnusedKeyService {
    /**
     * Generate unused keys
     *
     * @param total Total number of keys to generate
     */
    void generateUnusedKeys(int total);

    /**
     * Generate unused keys
     *
     * @param total  Total number of keys to generate
     * @param oldKey Old key to exclude
     */
    void generateUnusedKeys(int total, String oldKey);

    /**
     * Get an unused key
     *
     * @return An unused key
     */
    String getUnusedKey();
}
