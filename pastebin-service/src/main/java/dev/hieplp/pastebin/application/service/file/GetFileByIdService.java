package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.dto.file.result.FileResult;
import dev.hieplp.pastebin.application.port.in.file.GetFileByIdUseCase;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFileByIdService implements GetFileByIdUseCase {

    private final GetPastePort getPastePort;

    private final GetFilePort getFilePort;

    private final ReadStoragePort readStoragePort;

    @Override
    public FileResult get(GetFileByIdQuery query) {
        log.info("Get file fileId={}", query.fileId());

        var file = getFilePort.getById(query.fileId());
        var paste = getPastePort.getById(file.getPasteId());

        if (!paste.isAccessible()) {
            throw new NotFoundException("Paste not found");
        }

        var content = readContent(file);
        log.info("Got file fileId={} for pasteId={}", file.getFileId(), file.getPasteId());

        return FileResult.from(file, content);
    }

    private String readContent(PasteFile file) {
        try {
            var bytes = readStoragePort.read(file.getStorageKey());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Failed to read file content for fileId={} key={}: {}", file.getFileId(), file.getStorageKey(), e.getMessage());
            return "";
        }
    }

}
