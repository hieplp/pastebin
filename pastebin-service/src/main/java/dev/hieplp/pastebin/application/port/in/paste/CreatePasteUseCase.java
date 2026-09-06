package dev.hieplp.pastebin.application.port.in.paste;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;

public interface CreatePasteUseCase {

    CreatePasteResult create(CommandEnvelope<CreatePasteCommand> envelope);

}
