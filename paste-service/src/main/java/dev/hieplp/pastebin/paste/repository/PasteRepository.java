package dev.hieplp.pastebin.paste.repository;

import dev.hieplp.pastebin.paste.entity.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PasteRepository extends JpaRepository<PasteEntity, String>, JpaSpecificationExecutor<PasteEntity> {
    boolean existsByAliasAndOwnerIdAndDeletedIsFalse(String alias, String ownerId);
}
