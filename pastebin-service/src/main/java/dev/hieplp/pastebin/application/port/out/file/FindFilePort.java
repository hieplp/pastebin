package dev.hieplp.pastebin.application.port.out.file;

import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;

import java.util.List;

public interface FindFilePort {

    List<PasteFile> findByPasteId(PasteId pasteId);

}
