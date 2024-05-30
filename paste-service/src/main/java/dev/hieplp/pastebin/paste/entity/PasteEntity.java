package dev.hieplp.pastebin.paste.entity;

import dev.hieplp.pastebin.common.audit.AbstractAuditEntity;
import dev.hieplp.pastebin.common.enums.paste.PasteStatus;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String pasteId;

    private String alias;

    private String description;

    private String content;

    private String ownerId;

    private PasteStatus status;

    private boolean deleted;

    private String deletedBy;

    private Timestamp deletedAt;
}
