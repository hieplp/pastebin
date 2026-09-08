package dev.hieplp.pastebin.application.dto.file.command;

import dev.hieplp.pastebin.domain.vo.ContentType;
import dev.hieplp.pastebin.domain.vo.FileName;

public record CreateFileCommand(
        FileName name,
        ContentType contentType,
        long size,
        byte[] content
) {
}
