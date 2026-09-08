package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.Content;
import dev.hieplp.pastebin.domain.model.PasteFile;
import dev.hieplp.pastebin.domain.vo.FileName;
import dev.hieplp.pastebin.domain.vo.FileId;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import dev.hieplp.pastebin.domain.vo.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPasteServiceTest {

    @Mock
    private GetPastePort getPastePort;

    @Mock
    private SavePastePort savePastePort;

    @Mock
    private GetFilePort getFilePort;

    @InjectMocks
    private GetPasteService getPasteService;

    private Paste paste;

    @BeforeEach
    void setUp() {
        paste = Paste.create(
                Title.of("Test Title"),
                Alias.of("test-alias"),
                Content.of("Hello World"),
                Privacy.PUBLIC,
                Syntax.PLAINTEXT,
                null,
                false,
                Actor.system()
        );
    }

    @Test
    void get_activePasteWithFile_returnsResultWithFileId() {
        var query = new GetPasteQuery("test-alias");
        var file = new PasteFile();
        file.setFileId(FileId.of("file-123"));
        file.setName(FileName.of("hello.txt"));
        file.setSize(5);
        file.setStorageKey(StorageKey.of("k-1"));
        file.setPasteId(paste.getPasteId());

        when(getPastePort.getByIdOrAlias("test-alias")).thenReturn(paste);
        when(getFilePort.findByPasteId(paste.getPasteId())).thenReturn(List.of(file));

        var result = getPasteService.get(query);

        assertNotNull(result);
        assertEquals("test-alias", result.alias());
        assertEquals(1, result.files().size());
        assertEquals("file-123", result.files().getFirst().fileId());
        assertNull(result.files().getFirst().content());
    }

    @Test
    void get_expiredPaste_marksInactiveAndThrows() {
        paste.setExpiredAt(Instant.now().minus(1, ChronoUnit.HOURS));
        var query = new GetPasteQuery("test-alias");

        when(getPastePort.getByIdOrAlias("test-alias")).thenReturn(paste);

        var ex = assertThrows(NotFoundException.class, () -> getPasteService.get(query));
        assertEquals("Paste has expired", ex.getMessage());
        assertEquals(PasteStatus.INACTIVE, paste.getStatus());
        verify(savePastePort).save(paste);
    }

    @Test
    void get_burnAfterReadPaste_marksInactiveAndReturns() {
        paste.setBurnAfterRead(true);
        var query = new GetPasteQuery("test-alias");

        when(getPastePort.getByIdOrAlias("test-alias")).thenReturn(paste);
        when(getFilePort.findByPasteId(paste.getPasteId())).thenReturn(List.of());

        var result = getPasteService.get(query);

        assertNotNull(result);
        assertEquals(PasteStatus.INACTIVE, paste.getStatus());
        verify(savePastePort).save(paste);
    }

    @Test
    void get_inactivePaste_throwsNotFound() {
        paste.deactivate();
        var query = new GetPasteQuery("test-alias");

        when(getPastePort.getByIdOrAlias("test-alias")).thenReturn(paste);

        var ex = assertThrows(NotFoundException.class, () -> getPasteService.get(query));
        assertEquals("Paste not found", ex.getMessage());
    }

    @Test
    void get_missingFileOnDisk_handlesGracefully() {
        var query = new GetPasteQuery("test-alias");
        var file = new PasteFile();
        file.setFileId(FileId.of("file-123"));
        file.setName(FileName.of("hello.txt"));
        file.setSize(5);
        file.setStorageKey(StorageKey.of("k-1"));
        file.setPasteId(paste.getPasteId());

        when(getPastePort.getByIdOrAlias("test-alias")).thenReturn(paste);
        when(getFilePort.findByPasteId(paste.getPasteId())).thenReturn(List.of(file));

        var result = getPasteService.get(query);

        assertNotNull(result);
        assertEquals(1, result.files().size());
        assertNull(result.files().getFirst().content());
    }
}
