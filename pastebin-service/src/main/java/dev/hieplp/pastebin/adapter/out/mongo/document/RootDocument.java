package dev.hieplp.pastebin.adapter.out.mongo.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "roots")
public class RootDocument extends AuditableDocument {

    @Id
    private String rootId;

    @Indexed(unique = true)
    private String username;

    private String passwordHash;
    private String email;

}
