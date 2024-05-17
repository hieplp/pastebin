package dev.hieplp.pastebin.key.repository;

import dev.hieplp.pastebin.key.entity.UsedKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UsedKeyRepository extends JpaRepository<UsedKeyEntity, String> {
    List<UsedKeyEntity> findAllByKeyIn(Set<String> key);
}
