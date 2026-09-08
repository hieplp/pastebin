package dev.hieplp.pastebin.application.port.in.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.dto.file.result.DownloadFileResult;

public interface DownloadFileUseCase {

    DownloadFileResult download(GetFileByIdQuery query);

}
