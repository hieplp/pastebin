package dev.hieplp.pastebin.key.entity;

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
public class UsedKeyEntity {
    @Id
    private String key;

    @Version
    private long version;
}
