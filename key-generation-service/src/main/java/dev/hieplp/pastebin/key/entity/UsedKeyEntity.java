package dev.hieplp.pastebin.key.entity;

import dev.hieplp.pastebin.common.entity.AbstractAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "used_key")
public class UsedKeyEntity extends AbstractAuditEntity {
    @Id
    private String key;

    @Version
    private long version;
}
