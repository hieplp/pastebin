package dev.hieplp.pastebin.paste.store;

import dev.hieplp.pastebin.paste.entity.PasteEntity;

public interface PasteStore {
    /**
     * Save paste entity
     *
     * @param paste Paste entity
     * @return Paste entity
     */
    PasteEntity save(PasteEntity paste);

    /**
     * Find paste by paste id
     *
     * @param pasteId Paste id
     * @return Paste entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException If paste not found
     */
    PasteEntity findByPasteId(String pasteId);

    /**
     * Check if paste exists by alias and owner id
     *
     * @param alias   Alias
     * @param ownerId Owner id
     * @return True if paste exists, false otherwise
     */
    boolean existsByAliasAndOwnerId(String alias, String ownerId);
}