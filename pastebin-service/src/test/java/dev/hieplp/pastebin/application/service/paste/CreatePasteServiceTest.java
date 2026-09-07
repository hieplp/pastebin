package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.port.in.file.CreateFilesUseCase;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import dev.hieplp.pastebin.application.port.out.paste.SavePastePort;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.model.Paste;
import dev.hieplp.pastebin.domain.vo.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePasteServiceTest {

    @Mock
    private SavePastePort savePastePort;

    @Mock
    private GetPastePort getPastePort;

    @Mock
    private CreateFilesUseCase createFilesUseCase;

    @InjectMocks
    private CreatePasteService createPasteService;

    @Test
    void create_withoutContentAndFiles_throwsBadRequestException() {
        var command = new CreatePasteCommand("Title", "", Privacy.PUBLIC, Syntax.PLAINTEXT, null, null, false, List.of());
        var envelope = CommandEnvelope.of(command, Actor.system());

        var ex = assertThrows(BadRequestException.class, () -> createPasteService.create(envelope));
        assertEquals("Paste content or at least one file is required", ex.getMessage());
        verifyNoInteractions(savePastePort);
    }

    @Test
    void create_withDuplicateAlias_throwsBadRequestException() {
        var command = new CreatePasteCommand("Title", "Some content", Privacy.PUBLIC, Syntax.PLAINTEXT, "taken-alias", null, false, List.of());
        var envelope = CommandEnvelope.of(command, Actor.system());

        when(getPastePort.findByIdOrAlias("taken-alias")).thenReturn(Optional.of(mock(Paste.class)));

        var ex = assertThrows(BadRequestException.class, () -> createPasteService.create(envelope));
        assertEquals("Alias 'taken-alias' is already in use", ex.getMessage());
        verifyNoInteractions(savePastePort);
    }

    @Test
    void create_validPaste_savesSuccessfully() {
        var command = new CreatePasteCommand("Title", "Some content", Privacy.PUBLIC, Syntax.PLAINTEXT, "new-alias", null, false, List.of());
        var envelope = CommandEnvelope.of(command, Actor.system());

        when(getPastePort.findByIdOrAlias("new-alias")).thenReturn(Optional.empty());
        when(savePastePort.save(any(Paste.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = createPasteService.create(envelope);

        assertNotNull(result);
        assertEquals("new-alias", result.alias());
        verify(savePastePort).save(any(Paste.class));
    }
}
