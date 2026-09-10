package dev.hieplp.pastebin.adapter.out.mongo.repository;

import dev.hieplp.pastebin.adapter.out.mongo.document.RootDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RootRepository extends MongoRepository<RootDocument, String> {

    Optional<RootDocument> findByUsername(String username);

}
