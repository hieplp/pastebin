package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.dto.file.result.DownloadFileResult;
import dev.hieplp.pastebin.application.port.in.file.DownloadFileUseCase;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadFileService implements DownloadFileUseCase {

    private final GetPastePort getPastePort;

    private final GetFilePort getFilePort;

    private final ReadStoragePort readStoragePort;

    @Override
    public DownloadFileResult download(GetFileByIdQuery query) {
        log.info("Download file fileId={}", query.fileId());

        var file = getFilePort.getById(query.fileId());
        var paste = getPastePort.getById(file.getPasteId());

        if (!paste.isAccessible()) {
            throw new NotFoundException("Paste not found");
        }

        var content = readStoragePort.read(file.getStorageKey());
        log.info("Downloaded file fileId={} for pasteId={}", file.getFileId(), file.getPasteId());

        return DownloadFileResult.from(file, content);
    }

}
