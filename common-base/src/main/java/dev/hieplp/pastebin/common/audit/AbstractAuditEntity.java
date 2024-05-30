package dev.hieplp.pastebin.common.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity {

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private ZonedDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}
