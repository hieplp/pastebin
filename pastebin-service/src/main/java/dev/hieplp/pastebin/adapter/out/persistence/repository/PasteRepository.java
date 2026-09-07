package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasteRepository extends JpaRepository<PasteEntity, String> {

    Optional<PasteEntity> findByPasteIdOrAlias(String pasteId, String alias);

}