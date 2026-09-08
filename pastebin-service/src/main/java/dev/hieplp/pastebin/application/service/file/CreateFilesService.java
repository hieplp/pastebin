package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.file.command.CreateFilesCommand;
import dev.hieplp.pastebin.application.dto.file.result.CreateFilesResult;
import dev.hieplp.pastebin.application.port.in.file.CreateFilesUseCase;
import dev.hieplp.pastebin.application.port.out.file.SaveFilePort;
import dev.hieplp.pastebin.application.port.out.storage.UploadStoragePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFilesService implements CreateFilesUseCase {

    private final SaveFilePort saveFilePort;

    private final UploadStoragePort uploadStoragePort;

    @Override
    public CreateFilesResult create(CommandEnvelope<CreateFilesCommand> envelope) {
        var command = envelope.command();
        var actor = envelope.actor();
        log.info("Create {} files for pasteId={} by actor={}", command.files().size(), command.pasteId(), actor);

        var files = buildFiles(command);
        var saved = saveFilePort.saveAll(files);
        log.info("Saved {} files for pasteId={}", saved.size(), command.pasteId());

        return CreateFilesResult.from(saved);
    }

    private List<PasteFile> buildFiles(CreateFilesCommand command) {
        var files = command.files().stream()
                .map(f -> PasteFile.create(
                        command.pasteId(),
                        f.name(),
                        f.contentType(),
                        f.size(),
                        f.content()
                ))
                .toList();

        files.forEach(f -> f.setStorageKey(uploadStoragePort.upload(f)));
        return files;
    }

}
