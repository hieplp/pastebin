package dev.hieplp.pastebin.application.dto.file.command;

public record CreateFileCommand(
        String name,
        String contentType,
        long size,
        byte[] content
) {
}
