package dev.hieplp.pastebin.user.repository;

import dev.hieplp.pastebin.user.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity, String> {
}
