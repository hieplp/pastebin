package dev.hieplp.pastebin.application.dto.file.result;

public record CreateFileResult(
        String fileId,
        String name,
        String contentType,
        long size
) {
}
