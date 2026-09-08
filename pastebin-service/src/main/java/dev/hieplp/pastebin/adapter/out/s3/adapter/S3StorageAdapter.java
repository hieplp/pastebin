package dev.hieplp.pastebin.adapter.out.s3.adapter;

import dev.hieplp.pastebin.adapter.out.s3.config.S3StorageProperties;
import dev.hieplp.pastebin.application.port.out.storage.DeleteStoragePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.application.port.out.storage.UploadStoragePort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pastebin.storage.type", havingValue = "s3")
public class S3StorageAdapter implements UploadStoragePort, ReadStoragePort, DeleteStoragePort {

    private final S3Client s3;
    private final S3StorageProperties props;

    @Override
    public StorageKey upload(PasteFile file) {
        var key = file.getStorageKey();
        try {
            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(props.bucket())
                            .key(key.value())
                            .contentType(file.getContentType() != null ? file.getContentType().value() : null)
                            .build(),
                    RequestBody.fromBytes(file.getContent())
            );
            log.info("Stored file key={} bucket={}", key, props.bucket());
            return key;
        } catch (S3Exception e) {
            throw new IllegalStateException("Failed to store file " + key, e);
        }
    }

    @Override
    public byte[] read(StorageKey storageKey) {
        try {
            return s3.getObjectAsBytes(
                    GetObjectRequest.builder()
                            .bucket(props.bucket())
                            .key(storageKey.value())
                            .build()
            ).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new BadRequestException("File not found for key: " + storageKey);
        } catch (S3Exception e) {
            throw new IllegalStateException("Failed to read file " + storageKey, e);
        }
    }

    @Override
    public void delete(StorageKey storageKey) {
        if (storageKey == null || storageKey.value().isBlank()) {
            return;
        }
        try {
            s3.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(props.bucket())
                            .key(storageKey.value())
                            .build()
            );
            log.info("Deleted file key={} bucket={}", storageKey, props.bucket());
        } catch (S3Exception e) {
            log.warn("Failed to delete file from storage key={}: {}", storageKey, e.getMessage());
        }
    }

}
