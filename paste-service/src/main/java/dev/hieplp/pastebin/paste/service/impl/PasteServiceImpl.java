package dev.hieplp.pastebin.paste.service.impl;

import dev.hieplp.pastebin.common.enums.paste.PastePrivacy;
import dev.hieplp.pastebin.common.exception.AccessDeniedException;
import dev.hieplp.pastebin.common.exception.DuplicateException;
import dev.hieplp.pastebin.paste.entity.PasteEntity;
import dev.hieplp.pastebin.paste.payload.request.CreatePasteRequest;
import dev.hieplp.pastebin.paste.payload.request.UpdatePasteRequest;
import dev.hieplp.pastebin.paste.payload.response.CreatePasteResponse;
import dev.hieplp.pastebin.paste.payload.response.PasteResponse;
import dev.hieplp.pastebin.paste.payload.response.UpdatePasteResponse;
import dev.hieplp.pastebin.paste.service.PasteService;
import dev.hieplp.pastebin.paste.store.PasteStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasteServiceImpl implements PasteService {

    private final PasteStore pasteStore;

    @Override
    public CreatePasteResponse create(CreatePasteRequest request, String ownerId) {
        log.info("Create paste by {} with request: {}", ownerId, request);

        // Check if alias is already taken
        validatePasteAlias(request.alias(), ownerId);

        // Create paste entity
        var pasteEntity = PasteEntity.builder()
                .alias(request.alias())
                .content(request.content())
                .description(request.description())
                .privacy(request.privacy())
                .ownerId(ownerId)
                .expiredAt(request.expiredAt())
                .build();

        // Save paste entity
        pasteEntity = pasteStore.save(pasteEntity);

        return new CreatePasteResponse(pasteEntity);
    }

    @Override
    public UpdatePasteResponse update(String pasteId, UpdatePasteRequest request, String updatedBy) {
        log.info("Update paste: {} by {} with request: {}", pasteId, updatedBy, request);

        // Find paste entity by pasteId
        var pasteEntity = pasteStore.findByPasteId(pasteId);

        // Check if user is the owner of paste
        validatePasteOwner(pasteEntity, updatedBy);

        // Flag to check if any update is made
        boolean isUpdated = false;

        // If alias is provided and different from current alias, validate and update
        if (ObjectUtils.isNotEmpty(request.alias()) && !pasteEntity.getAlias().equals(request.alias())) {
            validatePasteAlias(request.alias(), pasteEntity.getOwnerId());
            pasteEntity.setAlias(request.alias());
            isUpdated = true;
        }

        // If description is provided, update
        if (ObjectUtils.isNotEmpty(request.description())) {
            pasteEntity.setDescription(request.description());
            isUpdated = true;
        }

        // If content is provided, update
        if (ObjectUtils.isNotEmpty(request.content())) {
            pasteEntity.setContent(request.content());
            isUpdated = true;
        }

        // If privacy is provided, update
        if (ObjectUtils.isNotEmpty(request.privacy())) {
            pasteEntity.setPrivacy(request.privacy());
            isUpdated = true;
        }

        // If expiredAt is provided, update
        if (ObjectUtils.isNotEmpty(request.expiredAt())) {
            pasteEntity.setExpiredAt(request.expiredAt());
            isUpdated = true;
        }

        // Save paste entity if any update is made
        if (isUpdated) {
            pasteEntity = pasteStore.save(pasteEntity);
        }

        return new UpdatePasteResponse(pasteEntity);
    }

    @Override
    public void delete(String pasteId, String deletedBy) {
        log.info("Delete paste: {} by {}", pasteId, deletedBy);

        // Find paste entity by pasteId
        var pasteEntity = pasteStore.findByPasteId(pasteId);

        // Check if user is the owner of paste
        validatePasteOwner(pasteEntity, deletedBy);

        // Soft delete paste entity
        pasteEntity
                .setDeleted(true)
                .setDeletedBy(deletedBy)
                .setDeletedAt(new Timestamp(System.currentTimeMillis()));
        pasteStore.save(pasteEntity);
    }

    @Override
    public PasteResponse get(String pasteId, String userId) {
        log.info("Get paste: {} by {}", pasteId, userId);

        // Find paste entity by pasteId
        var pasteEntity = pasteStore.findByPasteId(pasteId);

        // If paste is private, validate if user is the owner of paste
        if (PastePrivacy.PRIVATE.equals(pasteEntity.getPrivacy())) {
            validatePasteOwner(pasteEntity, userId);
        }

        // If paste is public, validate if paste is not expired
        var now = new Timestamp(System.currentTimeMillis());
        if (now.after(pasteEntity.getExpiredAt())) {
            log.info("Paste: {} is expired", pasteId);
            throw new AccessDeniedException("Paste is expired");
        }

        // Return paste response
        return new PasteResponse(pasteEntity);
    }

    @Override
    public PasteResponse getByUsernameAndAlias(String username, String alias) {
        log.info("Get paste by username: {} and alias: {}", username, alias);


        return null;
    }

    private void validatePasteAlias(String alias, String ownerId) {
        if (pasteStore.existsByAliasAndOwnerId(alias, ownerId)) {
            log.warn("Alias: {} is already taken", alias);
            throw new DuplicateException("Alias is already taken");
        }
    }

    private void validatePasteOwner(PasteEntity pasteEntity, String ownerId) {
        if (!pasteEntity.getOwnerId().equals(ownerId)) {
            log.warn("User: {} is not the owner of paste: {}", ownerId, pasteEntity.getPasteId());
            throw new AccessDeniedException("User is not the owner of paste");
        }
    }
}
