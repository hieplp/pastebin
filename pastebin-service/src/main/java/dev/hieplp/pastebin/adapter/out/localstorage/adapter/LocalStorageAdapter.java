package dev.hieplp.pastebin.adapter.out.localstorage.adapter;

import dev.hieplp.pastebin.adapter.out.localstorage.config.LocalStorageProperties;
import dev.hieplp.pastebin.application.port.out.file.ReadFilePort;
import dev.hieplp.pastebin.application.port.out.file.UploadFilePort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Repository
public class LocalStorageAdapter implements UploadFilePort, ReadFilePort {

    private final Path root;

    public LocalStorageAdapter(LocalStorageProperties props) {
        this.root = Path.of(props.root()).toAbsolutePath().normalize();
    }

    @Override
    public String upload(PasteFile file) {
        var key = file.getStorageKey();
        var target = root.resolve(key);
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
    public byte[] read(String storageKey) {
        try {
            var target = root.resolve(storageKey).normalize();
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

}
