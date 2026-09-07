package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasteFileRepository extends JpaRepository<PasteFileEntity, String> {

    List<PasteFileEntity> findByPasteId(String pasteId);

}
