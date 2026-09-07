package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.file.command.CreateFilesCommand;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;
import dev.hieplp.pastebin.application.port.in.file.CreateFilesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.CreatePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
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
    private final GetPastePort getPastePort;

    private final CreateFilesUseCase createFilesUseCase;

    @Transactional
    @Override
    public CreatePasteResult create(CommandEnvelope<CreatePasteCommand> envelope) {
        var command = envelope.command();
        var actor = envelope.actor();
        log.info("Create paste with title={} by actor={}", command.title(), actor);

        var content = command.content() != null ? command.content().trim() : "";
        var hasFiles = command.files() != null && !command.files().isEmpty();
        if (content.isEmpty() && !hasFiles) {
            throw new BadRequestException("Paste content or at least one file is required");
        }

        var alias = validateAndGetAlias(command);
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

        if (command.files() != null && !command.files().isEmpty()) {
            createFilesUseCase.create(envelope.withCommand(
                    new CreateFilesCommand(savedPaste.getPasteId(), command.files())
            ));
        }

        return toResult(savedPaste);
    }

    private String validateAndGetAlias(CreatePasteCommand command) {
        var alias = command.alias() != null && !command.alias().isBlank()
                ? command.alias().trim()
                : null;

        if (alias != null && getPastePort.findByIdOrAlias(alias).isPresent()) {
            throw new BadRequestException("Alias '" + alias + "' is already in use");
        }

        return alias;
    }

    private CreatePasteResult toResult(Paste paste) {
        return new CreatePasteResult(
                paste.getPasteId().value(),
                paste.getAlias()
        );
    }

}
