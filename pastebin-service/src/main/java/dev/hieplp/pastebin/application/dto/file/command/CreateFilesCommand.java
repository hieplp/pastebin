package dev.hieplp.pastebin.application.dto.file.command;

import dev.hieplp.pastebin.domain.vo.PasteId;

import java.util.List;

public record CreateFilesCommand(
        PasteId pasteId,
        List<CreateFileCommand> files
) {
}
