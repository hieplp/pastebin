package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;

import java.util.Optional;

public interface GetPastePort {

    Optional<Paste> findByIdOrAlias(String idOrAlias);

    default Paste getByIdOrAlias(String idOrAlias) {
        return findByIdOrAlias(idOrAlias)
                .orElseThrow(() -> new NotFoundException("Paste not found"));
    }

}
