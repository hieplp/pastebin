package dev.hieplp.pastebin.paste.repository;

import dev.hieplp.pastebin.paste.entity.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<PasteEntity, String> {
    boolean existsByAliasAndOwnerIdAndDeletedIsFalse(String alias, String ownerId);
}
