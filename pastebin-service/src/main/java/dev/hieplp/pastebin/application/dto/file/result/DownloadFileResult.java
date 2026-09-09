package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.util.Strings;

public record DownloadFileResult(
        String name,
        String contentType,
        byte[] content
) {

    public static DownloadFileResult from(PasteFile file, byte[] content) {
        var type = Strings.trim(file.getContentType() != null ? file.getContentType().value() : null)
                .orElse("application/octet-stream");
        var name = file.getName() != null ? file.getName().value() : "file";
        return new DownloadFileResult(name, type, content);
    }

}
