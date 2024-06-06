package dev.hieplp.pastebin.user.entity;

import dev.hieplp.pastebin.common.audit.AbstractAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "password")
public class PasswordEntity extends AbstractAuditEntity {

    @Id
    private String userId;

    private byte[] password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private UserEntity user;

}
