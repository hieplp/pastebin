package dev.hieplp.pastebin.paste.store.impl;

import dev.hieplp.pastebin.common.exception.NotFoundException;
import dev.hieplp.pastebin.paste.entity.PasteEntity;
import dev.hieplp.pastebin.paste.repository.PasteRepository;
import dev.hieplp.pastebin.paste.store.PasteStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasteStoreImpl implements PasteStore {

    private final PasteRepository pasteRepo;

    @Override
    public PasteEntity save(PasteEntity paste) {
        log.info("Save paste: {}", paste);
        return pasteRepo.save(paste);
    }

    @Override
    public PasteEntity findByPasteId(String pasteId) {
        log.info("Find paste by pasteId: {}", pasteId);

        final var paste = pasteRepo.findById(pasteId)
                .orElseThrow(() -> {
                    final var msg = "Paste not found with pasteId: " + pasteId;
                    log.error(msg);
                    return new NotFoundException(msg);
                });

        if (paste.isDeleted()) {
            final var msg = "Paste is deleted with pasteId: " + pasteId;
            log.error(msg);
            throw new NotFoundException(msg);
        }

        return paste;
    }

    @Override
    public boolean existsByAliasAndOwnerId(String alias, String ownerId) {
        log.info("Check if paste exists with alias: {} and ownerId: {}", alias, ownerId);
        return pasteRepo.existsByAliasAndOwnerIdAndDeletedIsFalse(alias, ownerId);
    }
}