package dev.hieplp.pastebin.application.dto.file.command;

import dev.hieplp.pastebin.domain.vo.PasteId;

public record DeleteFilesCommand(
        PasteId pasteId
) {
}
