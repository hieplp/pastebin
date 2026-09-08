package dev.hieplp.pastebin.adapter.out.localstorage.adapter;

import dev.hieplp.pastebin.adapter.out.localstorage.config.LocalStorageProperties;
import dev.hieplp.pastebin.application.port.out.storage.DeleteStoragePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.application.port.out.storage.UploadStoragePort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@ConditionalOnProperty(name = "pastebin.storage.type", havingValue = "local", matchIfMissing = true)
@Repository
public class LocalStorageAdapter implements UploadStoragePort, ReadStoragePort, DeleteStoragePort {

    private final Path root;

    public LocalStorageAdapter(LocalStorageProperties props) {
        this.root = Path.of(props.root()).toAbsolutePath().normalize();
    }

    @Override
    public StorageKey upload(PasteFile file) {
        var key = file.getStorageKey();
        var target = root.resolve(key.value());
        try {
            Files.createDirectories(target.getParent());
            Files.write(target, file.getContent());
            log.info("Stored file key={} at {}", key, target);
            return key;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store file " + key, e);
        }
    }

    @Override
    public byte[] read(StorageKey storageKey) {
        try {
            var target = root.resolve(storageKey.value()).normalize();
            if (!target.startsWith(root)) {
                throw new BadRequestException("Invalid storage key: " + storageKey);
            }
            if (!Files.exists(target)) {
                throw new BadRequestException("File not found for key: " + storageKey);
            }
            return Files.readAllBytes(target);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read file " + storageKey, e);
        }
    }

    @Override
    public void delete(StorageKey storageKey) {
        if (storageKey == null || storageKey.value().isBlank()) {
            return;
        }
        try {
            var target = root.resolve(storageKey.value()).normalize();
            if (target.startsWith(root)) {
                Files.deleteIfExists(target);
                log.info("Deleted file key={} at {}", storageKey, target);
            }
        } catch (IOException e) {
            log.warn("Failed to delete file from storage key={}: {}", storageKey, e.getMessage());
        }
    }

}
