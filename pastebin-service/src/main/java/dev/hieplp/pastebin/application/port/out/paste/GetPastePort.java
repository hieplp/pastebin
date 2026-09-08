package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface GetPastePort {

    Optional<Paste> findByIdOrAlias(String idOrAlias);

    List<Paste> findExpiredOrInactive(Instant now);

    default Paste getByIdOrAlias(String idOrAlias) {
        return findByIdOrAlias(idOrAlias)
                .orElseThrow(() -> new NotFoundException("Paste not found"));
    }

}
