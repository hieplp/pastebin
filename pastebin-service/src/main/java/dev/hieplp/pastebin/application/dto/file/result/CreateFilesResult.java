package dev.hieplp.pastebin.application.dto.file.result;

import dev.hieplp.pastebin.domain.model.PasteFile;

import java.util.List;

public record CreateFilesResult(
        List<CreateFileResult> files
) {

    public static CreateFilesResult from(List<PasteFile> files) {
        return new CreateFilesResult(
                files.stream()
                        .map(CreateFileResult::from)
                        .toList()
        );
    }
}
