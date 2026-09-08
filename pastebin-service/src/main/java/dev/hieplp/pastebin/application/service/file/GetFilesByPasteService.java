package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFilesByPasteQuery;
import dev.hieplp.pastebin.application.dto.file.result.GetFileResult;
import dev.hieplp.pastebin.application.port.in.file.GetFilesByPasteUseCase;
import dev.hieplp.pastebin.application.port.out.file.FindFilePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFilesByPasteService implements GetFilesByPasteUseCase {

    private final FindFilePort findFilePort;

    private final ReadStoragePort readStoragePort;

    @Override
    public List<GetFileResult> getFiles(GetFilesByPasteQuery query) {
        var files = findFilePort.findByPasteId(PasteId.of(query.pasteId()));
        return files.stream()
                .map(file -> GetFileResult.from(file, readContent(file)))
                .toList();
    }

    private String readContent(PasteFile file) {
        try {
            var bytes = readStoragePort.read(file.getStorageKey());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Failed to read file content for fileId={} key={}: {}",
                    file.getFileId() != null ? file.getFileId().value() : null,
                    file.getStorageKey(),
                    e.getMessage());
            return null;
        }
    }

}
