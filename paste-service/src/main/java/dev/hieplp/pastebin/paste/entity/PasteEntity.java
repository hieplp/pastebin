package dev.hieplp.pastebin.paste.entity;

import dev.hieplp.pastebin.common.audit.AbstractAuditEntity;
import dev.hieplp.pastebin.common.enums.paste.PastePrivacy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paste")
public class PasteEntity extends AbstractAuditEntity {
    @Id
    private String pasteId;

    private String alias;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String ownerId;

    private PastePrivacy privacy;

    private boolean deleted;

    private String deletedBy;

    private Timestamp deletedAt;

    private Timestamp expiredAt;
}
