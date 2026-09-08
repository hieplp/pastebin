package dev.hieplp.pastebin.application.port.in.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFilesByPasteQuery;
import dev.hieplp.pastebin.application.dto.file.result.GetFileResult;

import java.util.List;

public interface GetFilesByPasteUseCase {

    List<GetFileResult> getFiles(GetFilesByPasteQuery query);

}
