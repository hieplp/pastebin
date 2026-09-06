package dev.hieplp.pastebin.adapter.out.persistence.entity;

import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pastes")
public class PasteEntity extends AuditableEntity {

    @Id
    @Column(name = "paste_id", nullable = false, updatable = false)
    private String pasteId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Privacy privacy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Syntax syntax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PasteStatus status;

}