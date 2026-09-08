package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;

public record CreateFileResult(
        String fileId,
        String name,
        String contentType,
        long size
) {

    public static CreateFileResult from(PasteFile file) {
        return new CreateFileResult(
                file.getFileId().value(),
                file.getName() != null ? file.getName().value() : null,
                file.getContentType() != null ? file.getContentType().value() : null,
                file.getSize()
        );
    }

}
