package dev.hieplp.pastebin.adapter.in.schedule;

import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.port.in.file.DeleteOrphanFilesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.DeleteExpiredPastesUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasteCleanupSchedulerTest {

    @Mock
    private DeleteExpiredPastesUseCase deleteExpiredPastesUseCase;
    @Mock
    private DeleteOrphanFilesUseCase deleteOrphanFilesUseCase;
    @InjectMocks
    private PasteCleanupScheduler pasteCleanupScheduler;

    @Test
    void deleteExpiredOrInactivePastes_delegatesToUseCase() {
        pasteCleanupScheduler.deleteExpiredOrInactivePastes();

        verify(deleteExpiredPastesUseCase).delete(any(DeleteExpiredPastesCommand.class));
    }

    @Test
    void deleteOrphanFiles_delegatesToUseCase() {
        pasteCleanupScheduler.deleteOrphanFiles();

        verify(deleteOrphanFilesUseCase).delete();
    }
}
