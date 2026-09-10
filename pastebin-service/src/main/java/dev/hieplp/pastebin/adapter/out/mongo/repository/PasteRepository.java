package dev.hieplp.pastebin.adapter.out.mongo.repository;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteDocument;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PasteRepository extends MongoRepository<PasteDocument, String> {

    Optional<PasteDocument> findByPasteIdOrAlias(String pasteId, String alias);

    boolean existsByAlias(String alias);

    List<PasteDocument> findByStatusOrExpiredAtLessThanEqual(PasteStatus status, Instant expiredAt);

}
