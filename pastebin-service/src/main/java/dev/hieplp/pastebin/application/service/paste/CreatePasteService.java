package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.file.command.CreateFilesCommand;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;
import dev.hieplp.pastebin.application.port.in.file.CreateFilesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.CreatePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.ExistPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.Paste;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePasteService implements CreatePasteUseCase {

    private final SavePastePort savePastePort;
    private final ExistPastePort existPastePort;

    private final CreateFilesUseCase createFilesUseCase;

    @Transactional
    @Override
    public CreatePasteResult create(CommandEnvelope<CreatePasteCommand> envelope) {
        var command = envelope.command();
        var actor = envelope.actor();
        log.info("Create paste with title={} by actor={}", command.title(), actor);

        var content = command.content();
        var hasFiles = command.files() != null && !command.files().isEmpty();
        if (content.isBlank() && !hasFiles) {
            throw new BadRequestException("Paste content or at least one file is required");
        }

        var alias = command.alias();
        if (alias != null && existPastePort.existsByAlias(alias)) {
            throw new BadRequestException("Alias '" + alias.value() + "' is already in use");
        }

        var savedPaste = savePastePort.save(Paste.create(
                command.title(),
                alias,
                content,
                command.privacy(),
                command.syntax(),
                command.expiredAt(),
                command.burnAfterRead(),
                actor
        ));
        log.info("Paste is saved with pasteId={}", savedPaste.getPasteId());

        if (hasFiles) {
            createFilesUseCase.create(envelope.withCommand(
                    new CreateFilesCommand(savedPaste.getPasteId(), command.files())
            ));
        }

        return CreatePasteResult.from(savedPaste);
    }

}
