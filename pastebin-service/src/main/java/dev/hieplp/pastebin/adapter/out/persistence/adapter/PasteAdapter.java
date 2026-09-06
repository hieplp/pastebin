package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteRepository;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.model.Paste;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PasteAdapter implements SavePastePort {

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

}
