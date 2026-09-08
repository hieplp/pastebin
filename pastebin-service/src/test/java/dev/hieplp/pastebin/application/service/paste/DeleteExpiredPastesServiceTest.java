package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeletePasteResult;
import dev.hieplp.pastebin.application.port.in.paste.DeletePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.vo.PasteId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteExpiredPastesServiceTest {

    @Mock
    private GetPastePort getPastePort;

    @Mock
    private DeletePasteUseCase deletePasteUseCase;

    @InjectMocks
    private DeleteExpiredPastesService deleteExpiredPastesService;

    @Test
    void delete_whenNoExpiredPastes_doesNotDelete() {
        var command = DeleteExpiredPastesCommand.now();
        when(getPastePort.findExpiredOrInactive(command.time())).thenReturn(List.of());

        var result = deleteExpiredPastesService.delete(command);

        assertEquals(0, result.deletedCount());
        verifyNoInteractions(deletePasteUseCase);
    }

    @Test
    void delete_whenExpiredPastesFound_delegatesToUseCaseForEachPaste() {
        var p1 = PasteId.of("p-1");
        var paste1 = new Paste();
        paste1.setPasteId(p1);

        var p2 = PasteId.of("p-2");
        var paste2 = new Paste();
        paste2.setPasteId(p2);

        var cutoff = Instant.parse("2026-09-07T00:00:00Z");
        var command = new DeleteExpiredPastesCommand(cutoff);

        when(getPastePort.findExpiredOrInactive(cutoff)).thenReturn(List.of(paste1, paste2));
        when(deletePasteUseCase.delete(p1)).thenReturn(new DeletePasteResult("p-1"));
        when(deletePasteUseCase.delete(p2)).thenReturn(new DeletePasteResult("p-2"));

        var result = deleteExpiredPastesService.delete(command);

        assertEquals(2, result.deletedCount());
        verify(deletePasteUseCase).delete(p1);
        verify(deletePasteUseCase).delete(p2);
    }

}
