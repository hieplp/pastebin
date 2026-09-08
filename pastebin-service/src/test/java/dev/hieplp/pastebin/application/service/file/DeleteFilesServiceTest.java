package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.command.DeleteFilesCommand;
import dev.hieplp.pastebin.application.port.out.file.DeleteFilePort;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.storage.DeleteStoragePort;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.PasteId;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteFilesServiceTest {

    @Mock
    private GetFilePort getFilePort;

    @Mock
    private DeleteStoragePort deleteStoragePort;

    @Mock
    private DeleteFilePort deleteFilePort;

    @InjectMocks
    private DeleteFilesService deleteFilesService;

    @Test
    void delete_whenPasteHasFiles_deletesStorageKeysAndRecords() {
        var pasteId = PasteId.of("p-1");

        var file1 = new PasteFile();
        file1.setStorageKey(StorageKey.of("uploads/p-1/f-1.txt"));
        var file2 = new PasteFile();
        file2.setStorageKey(StorageKey.of("uploads/p-1/f-2.txt"));

        when(getFilePort.findByPasteId(pasteId)).thenReturn(List.of(file1, file2));

        var result = deleteFilesService.delete(new DeleteFilesCommand(pasteId));

        assertEquals(2, result.deletedCount());
        verify(getFilePort).findByPasteId(pasteId);
        verify(deleteStoragePort).delete(StorageKey.of("uploads/p-1/f-1.txt"));
        verify(deleteStoragePort).delete(StorageKey.of("uploads/p-1/f-2.txt"));
        verify(deleteFilePort).deleteByPasteId(pasteId);
    }

    @Test
    void delete_whenPasteHasNoFiles_deletesNoStorageAndZeroCount() {
        var pasteId = PasteId.of("p-2");

        when(getFilePort.findByPasteId(pasteId)).thenReturn(List.of());

        var result = deleteFilesService.delete(new DeleteFilesCommand(pasteId));

        assertEquals(0, result.deletedCount());
        verify(getFilePort).findByPasteId(pasteId);
        verifyNoInteractions(deleteStoragePort);
        verify(deleteFilePort).deleteByPasteId(pasteId);
    }

}
