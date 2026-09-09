package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.CreateRootCommand;
import dev.hieplp.pastebin.application.port.in.root.InitRootTokenUseCase;
import dev.hieplp.pastebin.application.port.out.password.HashPasswordPort;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import dev.hieplp.pastebin.application.port.out.root.SaveRootPort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import dev.hieplp.pastebin.domain.model.Root;
import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.Username;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRootServiceTest {

    @Mock
    private ExistRootPort existRootPort;

    @Mock
    private SaveRootPort saveRootPort;

    @Mock
    private InitRootTokenUseCase initRootTokenUseCase;

    @Mock
    private HashPasswordPort hashPasswordPort;

    @InjectMocks
    private CreateRootService createRootService;

    @Test
    void create_withInvalidToken_throwsUnauthorized() {
        var envelope = envelope("bad-token", "admin", "password1");
        when(initRootTokenUseCase.matches("bad-token")).thenReturn(false);

        var ex = assertThrows(UnauthorizedException.class, () -> createRootService.create(envelope));
        assertEquals("Invalid init token", ex.getMessage());
        verifyNoInteractions(saveRootPort);
        verify(initRootTokenUseCase, never()).consume();
    }

    @Test
    void create_whenRootExists_throwsBadRequest() {
        var envelope = envelope("init-token", "admin", "password1");
        when(initRootTokenUseCase.matches("init-token")).thenReturn(true);
        when(existRootPort.exists()).thenReturn(true);

        var ex = assertThrows(BadRequestException.class, () -> createRootService.create(envelope));
        assertEquals("Root already exists", ex.getMessage());
        verifyNoInteractions(saveRootPort);
        verify(initRootTokenUseCase, never()).consume();
    }

    @Test
    void create_withValidToken_savesHashedPasswordAndConsumesToken() {
        var envelope = envelope("init-token", "admin", "password1");
        when(initRootTokenUseCase.matches("init-token")).thenReturn(true);
        when(existRootPort.exists()).thenReturn(false);
        when(hashPasswordPort.hash("password1")).thenReturn("hashed");
        when(saveRootPort.save(any(Root.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = createRootService.create(envelope);

        assertEquals("admin", result.username());
        assertNotNull(result.rootId());
        verify(saveRootPort).save(argThat(root ->
                "admin".equals(root.getUsername().value())
                        && "hashed".equals(root.getPasswordHash())
                        && root.getEmail() == null
        ));
        verify(initRootTokenUseCase).consume();
    }

    private static CommandEnvelope<CreateRootCommand> envelope(String token, String username, String password) {
        return CommandEnvelope.of(
                new CreateRootCommand(token, Username.of(username), password),
                Actor.system()
        );
    }

}
