package dev.hieplp.pastebin.paste.store.impl;

import dev.hieplp.pastebin.common.exception.NotFoundException;
import dev.hieplp.pastebin.common.jpa.JpaSpecUtils;
import dev.hieplp.pastebin.paste.constants.PasteColumn;
import dev.hieplp.pastebin.paste.entity.PasteEntity;
import dev.hieplp.pastebin.paste.payload.request.GetOwnPastesRequest;
import dev.hieplp.pastebin.paste.repository.PasteRepository;
import dev.hieplp.pastebin.paste.store.PasteStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

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

    @Override
    public Page<PasteEntity> getOwnPastes(GetOwnPastesRequest request, String ownerId) {
        log.info("Get own pastes by ownerId: {} with request: {}", ownerId, request);

        final var specs = new LinkedList<Specification<PasteEntity>>();
        specs.add((JpaSpecUtils.equal(PasteColumn.OWNER_ID, ownerId)));

        return pasteRepo.findAll(JpaSpecUtils.where(specs), request.toPageable());
    }
}