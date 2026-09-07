package dev.hieplp.pastebin.application.port.in.file;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.file.command.CreateFilesCommand;
import dev.hieplp.pastebin.application.dto.file.result.CreateFilesResult;

public interface CreateFilesUseCase {

    CreateFilesResult create(CommandEnvelope<CreateFilesCommand> envelope);

}
