package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.application.dto.paste.result.PasteResult;

import java.util.Optional;

public interface CachePastePort {

    Optional<PasteResult> get(String idOrAlias);

    void put(PasteResult result);

    void evict(String idOrAlias);

}
