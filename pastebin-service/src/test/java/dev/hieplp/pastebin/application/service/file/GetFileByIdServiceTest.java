package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.storage.ReadStoragePort;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.Content;
import dev.hieplp.pastebin.domain.vo.FileId;
import dev.hieplp.pastebin.domain.vo.FileName;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import dev.hieplp.pastebin.domain.vo.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFileByIdServiceTest {

    @Mock
    private GetFilePort getFilePort;

    @Mock
    private ReadStoragePort readStoragePort;

    @Mock
    private GetPastePort getPastePort;

    @InjectMocks
    private GetFileByIdService getFileByIdService;

    private Paste paste;
    private PasteFile file;

    @BeforeEach
    void setUp() {
        paste = Paste.create(
                Title.of("Test"),
                Alias.of("test-alias"),
                Content.of("notes"),
                Privacy.PUBLIC,
                Syntax.PLAINTEXT,
                null,
                false,
                Actor.system()
        );
        file = new PasteFile();
        file.setFileId(FileId.of("f-1"));
        file.setPasteId(paste.getPasteId());
        file.setName(FileName.of("a.txt"));
        file.setSize(5);
        file.setStorageKey(StorageKey.of("k-1"));
    }

    @Test
    void get_readsContentForActivePaste() {
        when(getFilePort.getById("f-1")).thenReturn(file);
        when(getPastePort.getById(paste.getPasteId())).thenReturn(paste);
        when(readStoragePort.read(file.getStorageKey())).thenReturn("hello".getBytes(StandardCharsets.UTF_8));

        var result = getFileByIdService.get(new GetFileByIdQuery("f-1"));

        assertEquals("hello", result.content());
        assertEquals("a.txt", result.name());
    }

    @Test
    void get_allowsBurnedPaste() {
        paste.setBurnAfterRead(true);
        paste.deactivate();
        when(getFilePort.getById("f-1")).thenReturn(file);
        when(getPastePort.getById(paste.getPasteId())).thenReturn(paste);
        when(readStoragePort.read(file.getStorageKey())).thenReturn("hello".getBytes(StandardCharsets.UTF_8));

        var result = getFileByIdService.get(new GetFileByIdQuery("f-1"));

        assertEquals("hello", result.content());
    }

    @Test
    void get_inactivePaste_throwsNotFound() {
        paste.deactivate();
        when(getFilePort.getById("f-1")).thenReturn(file);
        when(getPastePort.getById(paste.getPasteId())).thenReturn(paste);

        var ex = assertThrows(NotFoundException.class,
                () -> getFileByIdService.get(new GetFileByIdQuery("f-1")));
        assertEquals("Paste not found", ex.getMessage());
        verifyNoInteractions(readStoragePort);
    }

    @Test
    void get_fileNotFound_throwsNotFound() {
        when(getFilePort.getById("unknown"))
                .thenThrow(new NotFoundException("File not found"));

        var ex = assertThrows(NotFoundException.class,
                () -> getFileByIdService.get(new GetFileByIdQuery("unknown")));
        assertEquals("File not found", ex.getMessage());
        verifyNoInteractions(readStoragePort, getPastePort);
    }
}
