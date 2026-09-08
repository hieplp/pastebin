package dev.hieplp.pastebin.application.port.out.file;

import dev.hieplp.pastebin.domain.vo.PasteId;

import java.util.List;

public interface DeleteFilePort {
    void deleteByPasteIds(List<PasteId> pasteIds);

    default void deleteByPasteId(PasteId pasteId) {
        deleteByPasteIds(List.of(pasteId));
    }

}
