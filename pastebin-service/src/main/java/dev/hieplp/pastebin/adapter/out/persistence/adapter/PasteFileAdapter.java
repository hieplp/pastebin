package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteFileRepository;
import dev.hieplp.pastebin.application.port.out.file.SaveFilePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PasteFileAdapter implements SaveFilePort {

    private final PasteFileRepository pasteFileRepo;
    private final PasteFileMapper pasteFileMapper;

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

}
