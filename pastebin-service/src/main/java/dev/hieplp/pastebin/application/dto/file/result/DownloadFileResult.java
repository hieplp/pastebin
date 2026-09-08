package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;

public record DownloadFileResult(
        String name,
        String contentType,
        byte[] content
) {

    public static DownloadFileResult from(PasteFile file, byte[] content) {
        var type = file.getContentType() != null ? file.getContentType().value() : null;
        if (type == null || type.isBlank()) {
            type = "application/octet-stream";
        }
        var name = file.getName() != null ? file.getName().value() : "file";
        return new DownloadFileResult(name, type, content);
    }

}
