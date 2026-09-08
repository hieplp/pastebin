package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.domain.vo.PasteId;

import java.util.List;

public interface DeletePastePort {


    void deleteAll(List<PasteId> pasteIds);

    void deleteById(PasteId pasteId);

}
