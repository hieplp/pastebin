package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;
import dev.hieplp.pastebin.application.port.in.paste.CreatePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
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

    @Transactional
    @Override
    public CreatePasteResult create(CommandEnvelope<CreatePasteCommand> envelope) {
        var command = envelope.command();
        var actor = envelope.actor();
        log.info("Create paste with title={} by actor={}", command.title(), actor);

        var savedPaste = savePastePort.save(Paste.create(
                command.title(),
                command.content(),
                command.privacy(),
                command.syntax(),
                actor
        ));
        log.info("Paste is saved with pasteId={}", savedPaste.getPasteId());

        return toResult(savedPaste);
    }

    private CreatePasteResult toResult(Paste paste) {
        return new CreatePasteResult(
                paste.getPasteId().value()
        );
    }

}
