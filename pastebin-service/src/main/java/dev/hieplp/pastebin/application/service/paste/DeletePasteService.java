package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeletePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeletePasteResult;
import dev.hieplp.pastebin.application.port.in.file.DeleteFilesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.DeletePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.DeletePastePort;
import dev.hieplp.pastebin.application.port.out.paste.CachePastePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletePasteService implements DeletePasteUseCase {

    private final DeletePastePort deletePastePort;

    private final CachePastePort cachePastePort;

    private final DeleteFilesUseCase deleteFilesUseCase;

    @Transactional
    @Override
    public DeletePasteResult delete(DeletePasteCommand command) {
        if (command == null || command.pasteId() == null) {
            log.info("Deleting paste skipped: null command or pasteId");
            return new DeletePasteResult(null);
        }

        var pasteId = command.pasteId();
        log.info("Deleting paste pasteId={}", pasteId);

        var deletedFiles = deleteFilesUseCase.delete(pasteId).deletedCount();
        deletePastePort.deleteById(pasteId);
        cachePastePort.evict(pasteId.value());

        log.info("Deleted paste pasteId={} and {} files", pasteId, deletedFiles);
        return new DeletePasteResult(pasteId.value());
    }

}
