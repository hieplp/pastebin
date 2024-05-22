package dev.hieplp.pastebin.auth.repository;

import dev.hieplp.pastebin.auth.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity, String> {
}
