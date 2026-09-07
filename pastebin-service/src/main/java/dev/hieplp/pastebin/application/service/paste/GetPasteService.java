package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.file.result.GetFileResult;
import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.dto.paste.result.GetPasteResult;
import dev.hieplp.pastebin.application.port.in.paste.GetPasteUseCase;
import dev.hieplp.pastebin.application.port.out.file.FindFilePort;
import dev.hieplp.pastebin.application.port.out.file.ReadFilePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.model.PasteFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPasteService implements GetPasteUseCase {

    private final GetPastePort getPastePort;
    private final SavePastePort savePastePort;

    private final FindFilePort findFilePort;
    private final ReadFilePort readFilePort;

    @Override
    public GetPasteResult get(GetPasteQuery query) {
        log.info("Get paste with idOrAlias={}", query.idOrAlias());
        var paste = getPastePort.getByIdOrAlias(query.idOrAlias());

        if (!paste.isActive()) {
            throw new NotFoundException("Paste not found");
        }

        if (paste.isExpired()) {
            paste.deactivate();
            savePastePort.save(paste);
            throw new NotFoundException("Paste has expired");
        }

        if (paste.isBurnAfterRead()) {
            paste.deactivate();
            savePastePort.save(paste);
            log.info("Burned paste pasteId={} after read", paste.getPasteId().value());
        }

        var files = findFilePort.findByPasteId(paste.getPasteId());

        return toResult(paste, files);
    }

    private GetPasteResult toResult(Paste paste, List<PasteFile> files) {
        return new GetPasteResult(
                paste.getPasteId().value(),
                paste.getTitle(),
                paste.getAlias(),
                paste.getContent(),
                paste.getPrivacy(),
                paste.getSyntax(),
                paste.getCreatedAt(),
                paste.getExpiredAt(),
                files.stream().map(this::toFileResult).toList()
        );
    }

    private GetFileResult toFileResult(PasteFile file) {
        String content = null;
        try {
            var bytes = readFilePort.read(file.getStorageKey());
            content = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Failed to read file content for fileId={} key={}: {}",
                    file.getFileId() != null ? file.getFileId().value() : null,
                    file.getStorageKey(),
                    e.getMessage());
        }
        return new GetFileResult(
                file.getFileId() != null ? file.getFileId().value() : null,
                file.getName(),
                file.getSize(),
                content
        );
    }

}
