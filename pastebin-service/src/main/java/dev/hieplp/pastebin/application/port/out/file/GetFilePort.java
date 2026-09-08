package dev.hieplp.pastebin.application.port.out.file;

import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;

import java.util.List;
import java.util.Optional;

public interface GetFilePort {

    List<PasteFile> findByPasteId(PasteId pasteId);

    Optional<PasteFile> findById(String fileId);

    default PasteFile getById(String fileId) {
        return findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

}
