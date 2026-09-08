package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;

public record FileResult(
        String fileId,
        String name,
        long size,
        String content
) {

    public static FileResult from(PasteFile file) {
        return from(file, null);
    }

    public static FileResult from(PasteFile file, String content) {
        return new FileResult(
                file.getFileId() != null ? file.getFileId().value() : null,
                file.getName() != null ? file.getName().value() : null,
                file.getSize(),
                content
        );
    }

}
