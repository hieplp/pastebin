package dev.hieplp.pastebin.application.port.out.file;

import dev.hieplp.pastebin.domain.model.PasteFile;

public interface UploadFilePort {

    String upload(PasteFile file);

}
