package dev.hieplp.pastebin.adapter.out.mongo.repository;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteFileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface PasteFileRepository extends MongoRepository<PasteFileDocument, String> {

    List<PasteFileDocument> findByPasteId(String pasteId);

    List<PasteFileDocument> findByPasteIdIn(Collection<String> pasteIds);

}
