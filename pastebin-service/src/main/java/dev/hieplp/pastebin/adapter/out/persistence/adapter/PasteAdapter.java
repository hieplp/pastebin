package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteRepository;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.model.Paste;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PasteAdapter implements SavePastePort, GetPastePort {

    private final PasteRepository pasteRepo;
    private final PasteMapper pasteMapper;

    @Override
    public Paste save(Paste paste) {
        return pasteMapper.toModel(
                pasteRepo.save(
                        pasteMapper.toEntity(paste)
                )
        );
    }

    @Override
    public Optional<Paste> findByIdOrAlias(String idOrAlias) {
        if (idOrAlias == null || idOrAlias.isBlank()) {
            return Optional.empty();
        }
        var trimmed = idOrAlias.trim();
        return pasteRepo.findByPasteIdOrAlias(trimmed, trimmed)
                .map(pasteMapper::toModel);
    }

}
