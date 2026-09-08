package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PasteFileRepository extends JpaRepository<PasteFileEntity, String> {

    List<PasteFileEntity> findByPasteId(String pasteId);

    List<PasteFileEntity> findByPasteIdIn(Collection<String> pasteIds);

    @Query("select e.storageKey from PasteFileEntity e")
    List<String> findAllStorageKeys();

}
