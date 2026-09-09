package dev.hieplp.pastebin.application.port.in.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.LoginRootCommand;
import dev.hieplp.pastebin.application.dto.auth.LoginResult;

public interface LoginRootUseCase {

    LoginResult login(CommandEnvelope<LoginRootCommand> envelope);

}
