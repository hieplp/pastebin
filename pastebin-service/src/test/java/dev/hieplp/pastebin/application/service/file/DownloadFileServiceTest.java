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
import dev.hieplp.pastebin.domain.vo.ContentType;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadFileServiceTest {

    @Mock
    private GetFilePort getFilePort;

    @Mock
    private ReadStoragePort readStoragePort;

    @Mock
    private GetPastePort getPastePort;

    @InjectMocks
    private DownloadFileService downloadFileService;

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
        file.setName(FileName.of("a.bin"));
        file.setContentType(ContentType.of("application/octet-stream"));
        file.setSize(4);
        file.setStorageKey(StorageKey.of("k-1"));
    }

    @Test
    void download_returnsBytesForAccessiblePaste() {
        var bytes = new byte[]{0x00, 0x01, (byte) 0xff, 0x7f};
        when(getFilePort.getById("f-1")).thenReturn(file);
        when(getPastePort.getById(paste.getPasteId())).thenReturn(paste);
        when(readStoragePort.read(file.getStorageKey())).thenReturn(bytes);

        var result = downloadFileService.download(new GetFileByIdQuery("f-1"));

        assertArrayEquals(bytes, result.content());
        assertEquals("a.bin", result.name());
        assertEquals("application/octet-stream", result.contentType());
    }

    @Test
    void download_inactivePaste_throwsNotFound() {
        paste.deactivate();
        when(getFilePort.getById("f-1")).thenReturn(file);
        when(getPastePort.getById(paste.getPasteId())).thenReturn(paste);

        var ex = assertThrows(NotFoundException.class,
                () -> downloadFileService.download(new GetFileByIdQuery("f-1")));
        assertEquals("Paste not found", ex.getMessage());
        verifyNoInteractions(readStoragePort);
    }

}
