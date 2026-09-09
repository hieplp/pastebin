package dev.hieplp.pastebin.adapter.out.persistence.repository;

import dev.hieplp.pastebin.adapter.out.persistence.entity.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RootRepository extends JpaRepository<RootEntity, String> {

    Optional<RootEntity> findByUsername(String username);

}
