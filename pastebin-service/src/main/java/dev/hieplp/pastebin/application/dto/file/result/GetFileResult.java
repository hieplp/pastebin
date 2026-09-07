package dev.hieplp.pastebin.application.dto.file.result;

public record GetFileResult(
        String fileId,
        String name,
        long size,
        String content
) {
}
