package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.persistence.mapper.VoMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteRepository;
import dev.hieplp.pastebin.application.port.out.paste.DeletePastePort;
import dev.hieplp.pastebin.application.port.out.paste.ExistPastePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PasteAdapter implements SavePastePort, GetPastePort, DeletePastePort, ExistPastePort {

    private final VoMapper voMapper;
    private final PasteMapper pasteMapper;

    private final PasteRepository pasteRepo;

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

    @Override
    public Optional<Paste> findById(PasteId pasteId) {
        if (pasteId == null) {
            return Optional.empty();
        }
        return pasteRepo.findById(pasteId.value())
                .map(pasteMapper::toModel);
    }

    @Override
    public boolean existsByAlias(Alias alias) {
        if (alias == null || alias.value().isBlank()) {
            return false;
        }
        return pasteRepo.existsByAlias(alias.value());
    }

    @Transactional
    @Override
    public List<Paste> findExpiredOrInactive(Instant now) {
        return pasteMapper.toModels(
                pasteRepo.findByStatusOrExpiredAtLessThanEqual(PasteStatus.INACTIVE, now)
        );
    }

    @Transactional
    @Override
    public void deleteAll(List<PasteId> pasteIds) {
        if (pasteIds == null || pasteIds.isEmpty()) {
            return;
        }
        var ids = voMapper.toPasteIdStrings(pasteIds);
        pasteRepo.deleteAllByIdInBatch(ids);
        log.info("Deleted {} pastes", ids.size());
    }

    @Transactional
    @Override
    public void deleteById(PasteId pasteId) {
        if (pasteId == null) {
            return;
        }
        pasteRepo.deleteById(pasteId.value());
        log.info("Deleted paste pasteId={}", pasteId);
    }

}
