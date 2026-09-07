package dev.hieplp.pastebin.application.dto.file.result;

import java.util.List;

public record CreateFilesResult(
        List<CreateFileResult> files
) {
}
