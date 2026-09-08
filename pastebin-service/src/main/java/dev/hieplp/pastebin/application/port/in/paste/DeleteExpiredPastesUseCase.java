package dev.hieplp.pastebin.application.port.in.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeleteExpiredPastesResult;

public interface DeleteExpiredPastesUseCase {

    DeleteExpiredPastesResult delete(DeleteExpiredPastesCommand command);

    default DeleteExpiredPastesResult delete() {
        return delete(DeleteExpiredPastesCommand.now());
    }

}
