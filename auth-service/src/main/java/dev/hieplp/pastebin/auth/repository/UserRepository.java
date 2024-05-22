package dev.hieplp.pastebin.auth.repository;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
