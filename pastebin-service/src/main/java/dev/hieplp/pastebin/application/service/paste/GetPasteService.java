package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.file.result.FileResult;
import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.dto.paste.result.PasteResult;
import dev.hieplp.pastebin.application.port.in.paste.GetPasteUseCase;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.paste.CachePastePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPasteService implements GetPasteUseCase {

    private final GetPastePort getPastePort;
    private final SavePastePort savePastePort;
    private final CachePastePort cachePastePort;

    private final GetFilePort getFilePort;

    @Override
    public PasteResult get(GetPasteQuery query) {
        var idOrAlias = query.idOrAlias();
        log.info("Get paste with idOrAlias={}", idOrAlias);

        return cachePastePort.get(idOrAlias)
                .filter(PasteResult::isAvailable)
                .orElseGet(() -> loadPaste(idOrAlias));
    }

    private PasteResult loadPaste(String idOrAlias) {
        var paste = getPastePort.getByIdOrAlias(idOrAlias);

        if (!paste.isActive()) {
            throw new NotFoundException("Paste not found");
        }

        if (paste.isExpired()) {
            deactivateExpired(paste);
        }

        if (paste.isBurnAfterRead()) {
            burnAfterRead(paste);
        }

        var result = PasteResult.from(paste, getFiles(paste));
        if (!paste.isBurnAfterRead()) {
            cachePastePort.put(result);
        }

        return result;
    }

    private void deactivateExpired(Paste paste) {
        paste.deactivate();
        savePastePort.save(paste);
        throw new NotFoundException("Paste has expired");
    }

    private void burnAfterRead(Paste paste) {
        paste.deactivate();
        savePastePort.save(paste);
        log.info("Burned paste pasteId={} after read", paste.getPasteId());
    }

    private List<FileResult> getFiles(Paste paste) {
        return getFilePort.findByPasteId(paste.getPasteId()).stream()
                .map(FileResult::from)
                .toList();
    }

}
