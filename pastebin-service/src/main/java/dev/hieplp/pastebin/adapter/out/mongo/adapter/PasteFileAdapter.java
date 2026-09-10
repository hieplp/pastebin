package dev.hieplp.pastebin.adapter.out.mongo.adapter;

import dev.hieplp.pastebin.adapter.out.mongo.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.out.mongo.mapper.VoMapper;
import dev.hieplp.pastebin.adapter.out.mongo.repository.PasteFileRepository;
import dev.hieplp.pastebin.application.port.out.file.DeleteFilePort;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.file.SaveFilePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "pastebin.persistence.type", havingValue = "mongo")
public class PasteFileAdapter implements SaveFilePort, GetFilePort, DeleteFilePort {

    private final VoMapper voMapper;
    private final PasteFileMapper pasteFileMapper;

    private final PasteFileRepository pasteFileRepo;

    @Override
    public PasteFile save(PasteFile file) {
        return pasteFileMapper.toModel(
                pasteFileRepo.save(
                        pasteFileMapper.toDocument(file)
                )
        );
    }

    @Override
    public List<PasteFile> saveAll(List<PasteFile> files) {
        return pasteFileMapper.toModels(
                pasteFileRepo.saveAll(
                        pasteFileMapper.toDocuments(files)
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
    public Optional<PasteFile> findById(String fileId) {
        return pasteFileRepo.findById(fileId)
                .map(pasteFileMapper::toModel);
    }

    @Override
    public List<StorageKey> findAllStorageKeys() {
        // ponytail: full scan; project storageKey if paste_files gets large
        return pasteFileRepo.findAll().stream()
                .map(doc -> voMapper.stringToStorageKey(doc.getStorageKey()))
                .toList();
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
        pasteFileRepo.deleteAll(files);
        log.info("Deleted {} files for {} pastes", files.size(), ids.size());
    }

}
