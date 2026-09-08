package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.vo.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasteFile {

    private FileId fileId;
    private PasteId pasteId;
    private FileName name;
    private ContentType contentType;
    private long size;
    private StorageKey storageKey;
    private byte[] content;

    public static PasteFile create(
            PasteId pasteId,
            FileName name,
            ContentType contentType,
            long size,
            byte[] content
    ) {
        var file = new PasteFile();
        var fileId = FileId.uuid();

        file.setFileId(fileId);
        file.setPasteId(pasteId);
        file.setName(name);
        file.setContentType(contentType);
        file.setSize(size);
        file.setContent(content);
        file.setStorageKey(StorageKey.of(fileId.value()));

        return file;
    }

}
