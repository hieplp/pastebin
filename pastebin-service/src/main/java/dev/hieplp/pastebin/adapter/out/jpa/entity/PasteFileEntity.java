package dev.hieplp.pastebin.adapter.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paste_files")
public class PasteFileEntity {

    @Id
    @Column(name = "file_id", nullable = false, updatable = false)
    private String fileId;

    @Column(name = "paste_id", nullable = false, updatable = false)
    private String pasteId;

    @Column(nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(nullable = false)
    private long size;

    @Column(name = "storage_key", nullable = false, unique = true)
    private String storageKey;

}
