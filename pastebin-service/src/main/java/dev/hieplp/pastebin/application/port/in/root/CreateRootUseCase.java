package dev.hieplp.pastebin.application.port.in.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.CreateRootCommand;
import dev.hieplp.pastebin.application.dto.root.result.CreateRootResult;

public interface CreateRootUseCase {

    CreateRootResult create(CommandEnvelope<CreateRootCommand> envelope);

}
