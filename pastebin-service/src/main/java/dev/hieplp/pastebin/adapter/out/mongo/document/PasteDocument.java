package dev.hieplp.pastebin.adapter.out.mongo.document;

import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "pastes")
public class PasteDocument extends AuditableDocument {

    @Id
    private String pasteId;

    private String title;

    // ponytail: sparse unique so missing alias != duplicate null (Postgres unique allows multiple NULLs)
    @Indexed(unique = true, partialFilter = "{ 'alias': { $type: 'string' } }")
    private String alias;

    private String content;
    private Privacy privacy;
    private Syntax syntax;
    private Instant expiredAt;
    private boolean burnAfterRead;
    private PasteStatus status;

}
