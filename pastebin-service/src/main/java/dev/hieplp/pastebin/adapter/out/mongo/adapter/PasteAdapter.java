package dev.hieplp.pastebin.adapter.out.mongo.adapter;

import dev.hieplp.pastebin.adapter.out.mongo.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.mongo.mapper.VoMapper;
import dev.hieplp.pastebin.adapter.out.mongo.repository.PasteRepository;
import dev.hieplp.pastebin.application.port.out.paste.DeletePastePort;
import dev.hieplp.pastebin.application.port.out.paste.ExistPastePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.util.Strings;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pastebin.persistence.type", havingValue = "mongo")
public class PasteAdapter implements SavePastePort, GetPastePort, DeletePastePort, ExistPastePort {

    private final VoMapper voMapper;
    private final PasteMapper pasteMapper;

    private final PasteRepository pasteRepo;

    @Override
    public Paste save(Paste paste) {
        return pasteMapper.toModel(
                pasteRepo.save(
                        pasteMapper.toDocument(paste)
                )
        );
    }

    @Override
    public Optional<Paste> findByIdOrAlias(String idOrAlias) {
        return Strings.trim(idOrAlias)
                .flatMap(id -> pasteRepo.findByPasteIdOrAlias(id, id).map(pasteMapper::toModel));
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
        return alias != null && Strings.trim(alias.value()).filter(pasteRepo::existsByAlias).isPresent();
    }

    @Override
    public List<Paste> findExpiredOrInactive(Instant now) {
        return pasteMapper.toModels(
                pasteRepo.findByStatusOrExpiredAtLessThanEqual(PasteStatus.INACTIVE, now)
        );
    }

    @Override
    public void deleteAll(List<PasteId> pasteIds) {
        if (pasteIds == null || pasteIds.isEmpty()) {
            return;
        }
        var ids = voMapper.toPasteIdStrings(pasteIds);
        pasteRepo.deleteAllById(ids);
        log.info("Deleted {} pastes", ids.size());
    }

    @Override
    public void deleteById(PasteId pasteId) {
        if (pasteId == null) {
            return;
        }
        pasteRepo.deleteById(pasteId.value());
        log.info("Deleted paste pasteId={}", pasteId);
    }

}
