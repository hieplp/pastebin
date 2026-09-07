package dev.hieplp.pastebin.application.port.out.file;

import dev.hieplp.pastebin.domain.model.PasteFile;

import java.util.List;

public interface SaveFilePort {

    PasteFile save(PasteFile file);

    List<PasteFile> saveAll(List<PasteFile> files);

}
