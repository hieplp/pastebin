package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.command.DeleteFilesCommand;
import dev.hieplp.pastebin.application.dto.file.result.DeleteFilesResult;
import dev.hieplp.pastebin.application.port.in.file.DeleteFilesUseCase;
import dev.hieplp.pastebin.application.port.out.file.DeleteFilePort;
import dev.hieplp.pastebin.application.port.out.file.FindFilePort;
import dev.hieplp.pastebin.application.port.out.storage.DeleteStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteFilesService implements DeleteFilesUseCase {

    private final FindFilePort findFilePort;
    private final DeleteFilePort deleteFilePort;

    private final DeleteStoragePort deleteStoragePort;

    @Override
    public DeleteFilesResult delete(DeleteFilesCommand command) {
        if (command == null || command.pasteId() == null) {
            return new DeleteFilesResult(0);
        }

        var pasteId = command.pasteId();
        log.info("Deleting files of paste pasteId={}", pasteId);

        var files = findFilePort.findByPasteId(pasteId);
        for (var file : files) {
            deleteStoragePort.delete(file.getStorageKey());
        }

        deleteFilePort.deleteByPasteId(pasteId);

        log.info("Deleted {} files of paste pasteId={}", files.size(), pasteId);
        return new DeleteFilesResult(files.size());
    }

}
