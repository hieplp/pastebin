package dev.hieplp.pastebin.adapter.in.schedule;

import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.port.in.file.DeleteOrphanFilesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.DeleteExpiredPastesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PasteCleanupScheduler {

    private final DeleteExpiredPastesUseCase deleteExpiredPastesUseCase;
    private final DeleteOrphanFilesUseCase deleteOrphanFilesUseCase;

    @Scheduled(cron = "${pastebin.cleanup.expired-paste-cron}")
    void deleteExpiredOrInactivePastes() {
        deleteExpiredPastesUseCase.delete(new DeleteExpiredPastesCommand(Instant.now()));
    }

    @Scheduled(cron = "${pastebin.cleanup.orphan-cron}")
    void deleteOrphanFiles() {
        deleteOrphanFilesUseCase.delete();
    }

}
