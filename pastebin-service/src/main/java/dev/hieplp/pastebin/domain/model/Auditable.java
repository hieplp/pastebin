package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.vo.Actor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class Auditable {

    private Actor createdBy;
    private Instant createdAt;
    private Actor lastModifiedBy;
    private Instant lastModifiedAt;

    protected void setCreator(Actor creator) {
        this.createdBy = creator;
        this.createdAt = Instant.now();
        this.lastModifiedBy = creator;
        this.lastModifiedAt = Instant.now();
    }

}
