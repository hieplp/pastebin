package dev.hieplp.pastebin.adapter.out.file;

import dev.hieplp.pastebin.domain.model.PasteFile;

public interface UploadFilePort {

    String upload(PasteFile file);

}
