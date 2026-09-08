package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;

public record GetFileResult(
        String fileId,
        String name,
        long size,
        String content
) {

    public static GetFileResult from(PasteFile file, String content) {
        return new GetFileResult(
                file.getFileId() != null ? file.getFileId().value() : null,
                file.getName() != null ? file.getName().value() : null,
                file.getSize(),
                content
        );
    }

}
