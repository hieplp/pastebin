package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteEntity;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PasteRepository extends JpaRepository<PasteEntity, String> {

    Optional<PasteEntity> findByPasteIdOrAlias(String pasteId, String alias);

    boolean existsByAlias(String alias);

    List<PasteEntity> findByStatusOrExpiredAtLessThanEqual(PasteStatus status, Instant expiredAt);

}