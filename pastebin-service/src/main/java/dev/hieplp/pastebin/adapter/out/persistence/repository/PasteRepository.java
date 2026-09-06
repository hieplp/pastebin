package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<PasteEntity, String> {
}