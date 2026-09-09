package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.file.result.DeleteFilesResult;
import dev.hieplp.pastebin.application.dto.paste.command.DeletePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeletePasteResult;
import dev.hieplp.pastebin.application.port.in.file.DeleteFilesUseCase;
import dev.hieplp.pastebin.application.port.out.paste.DeletePastePort;
import dev.hieplp.pastebin.application.port.out.paste.CachePastePort;
import dev.hieplp.pastebin.domain.vo.PasteId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletePasteServiceTest {

    @Mock
    private DeleteFilesUseCase deleteFilesUseCase;

    @Mock
    private DeletePastePort deletePastePort;

    @Mock
    private CachePastePort cachePastePort;

    @InjectMocks
    private DeletePasteService deletePasteService;

    @Test
    void delete_whenCommandOrPasteIdNull_returnsNullResult() {
        var r1 = deletePasteService.delete((DeletePasteCommand) null);
        var r2 = deletePasteService.delete(new DeletePasteCommand(null));

        assertNull(r1.pasteId());
        assertNull(r2.pasteId());
        verifyNoInteractions(deleteFilesUseCase, deletePastePort, cachePastePort);
    }

    @Test
    void delete_whenPasteHasFiles_deletesFilesThenPaste() {
        var pasteId = PasteId.of("p-1");

        when(deleteFilesUseCase.delete(pasteId)).thenReturn(new DeleteFilesResult(2));

        var result = deletePasteService.delete(new DeletePasteCommand(pasteId));

        assertEquals("p-1", result.pasteId());
        verify(deleteFilesUseCase).delete(pasteId);
        verify(deletePastePort).deleteById(pasteId);
        verify(cachePastePort).evict("p-1");
    }

    @Test
    void delete_whenPasteHasNoFiles_deletesPasteOnly() {
        var pasteId = PasteId.of("p-2");

        when(deleteFilesUseCase.delete(pasteId)).thenReturn(new DeleteFilesResult(0));

        var result = deletePasteService.delete(pasteId);

        assertEquals("p-2", result.pasteId());
        verify(deleteFilesUseCase).delete(pasteId);
        verify(deletePastePort).deleteById(pasteId);
        verify(cachePastePort).evict("p-2");
    }

    @Test
    void delete_byStringId_delegatesWithPasteId() {
        var pasteId = PasteId.of("p-3");
        when(deleteFilesUseCase.delete(pasteId)).thenReturn(new DeleteFilesResult(0));

        var result = deletePasteService.delete("p-3");

        assertEquals("p-3", result.pasteId());
        verify(deleteFilesUseCase).delete(pasteId);
        verify(deletePastePort).deleteById(pasteId);
        verify(cachePastePort).evict("p-3");
    }

}
