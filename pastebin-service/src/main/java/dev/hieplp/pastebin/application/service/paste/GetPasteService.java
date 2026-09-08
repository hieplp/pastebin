package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.file.query.GetFilesByPasteQuery;
import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.dto.paste.result.GetPasteResult;
import dev.hieplp.pastebin.application.port.in.file.GetFilesByPasteUseCase;
import dev.hieplp.pastebin.application.port.in.paste.GetPasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPasteService implements GetPasteUseCase {

    private final GetPastePort getPastePort;
    private final SavePastePort savePastePort;

    private final GetFilesByPasteUseCase getFilesByPasteUseCase;

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
            log.info("Burned paste pasteId={} after read", paste.getPasteId());
        }

        var files = getFilesByPasteUseCase.getFiles(new GetFilesByPasteQuery(paste.getPasteId().value()));

        return GetPasteResult.from(paste, files);
    }

}
