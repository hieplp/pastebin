package dev.hieplp.pastebin.application.dto.paste.command;

import java.time.Instant;

public record DeleteExpiredPastesCommand(
        Instant time
) {

    public DeleteExpiredPastesCommand {
        if (time == null) {
            time = Instant.now();
        }
    }

    public static DeleteExpiredPastesCommand now() {
        return new DeleteExpiredPastesCommand(Instant.now());
    }

    public static DeleteExpiredPastesCommand of(Instant time) {
        return new DeleteExpiredPastesCommand(time);
    }

}
