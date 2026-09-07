package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteFileRepository extends JpaRepository<PasteFileEntity, String> {
}
