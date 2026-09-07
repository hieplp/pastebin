package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.vo.FileId;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasteFile {

    private FileId fileId;
    private PasteId pasteId;
    private String name;
    private String contentType;
    private long size;
    private String storageKey;
    private byte[] content;

    public static PasteFile create(
            PasteId pasteId,
            String name,
            String contentType,
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
        file.setStorageKey(fileId.value());

        return file;
    }

}
