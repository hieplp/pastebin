package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.out.persistence.mapper.VoPersistenceMapperImpl;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteFileRepository;
import dev.hieplp.pastebin.application.port.out.file.DeleteFilePort;
import dev.hieplp.pastebin.application.port.out.file.FindFilePort;
import dev.hieplp.pastebin.application.port.out.file.SaveFilePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PasteFileAdapter implements SaveFilePort, FindFilePort, DeleteFilePort {

    private final PasteFileRepository pasteFileRepo;
    private final PasteFileMapper pasteFileMapper;
    private final VoPersistenceMapperImpl voMapper;

    @Override
    public PasteFile save(PasteFile file) {
        return pasteFileMapper.toModel(
                pasteFileRepo.save(
                        pasteFileMapper.toEntity(file)
                )
        );
    }

    @Override
    public List<PasteFile> saveAll(List<PasteFile> files) {
        return pasteFileMapper.toModels(
                pasteFileRepo.saveAll(
                        pasteFileMapper.toEntities(files)
                )
        );
    }

    @Override
    public List<PasteFile> findByPasteId(PasteId pasteId) {
        return pasteFileMapper.toModels(
                pasteFileRepo.findByPasteId(pasteId.value())
        );
    }

    @Override
    public void deleteByPasteIds(List<PasteId> pasteIds) {
        if (pasteIds == null || pasteIds.isEmpty()) {
            return;
        }
        var ids = voMapper.toPasteIdStrings(pasteIds);
        var files = pasteFileRepo.findByPasteIdIn(ids);
        if (files.isEmpty()) {
            return;
        }
        pasteFileRepo.deleteAllInBatch(files);
        log.info("Deleted {} files for {} pastes", files.size(), ids.size());
    }

}
