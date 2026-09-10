package dev.hieplp.pastebin.adapter.out.mongo.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "paste_files")
public class PasteFileDocument {

    @Id
    private String fileId;

    @Indexed
    private String pasteId;

    private String name;
    private String contentType;
    private long size;

    @Indexed(unique = true)
    private String storageKey;

}
