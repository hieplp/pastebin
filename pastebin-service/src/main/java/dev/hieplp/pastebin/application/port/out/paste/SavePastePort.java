package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.domain.model.Paste;

public interface SavePastePort {

    Paste save(Paste paste);

}
