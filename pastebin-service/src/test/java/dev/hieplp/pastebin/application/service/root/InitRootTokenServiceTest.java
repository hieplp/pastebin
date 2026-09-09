package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitRootTokenServiceTest {

    @Mock
    private ExistRootPort existRootPort;

    @InjectMocks
    private InitRootTokenService initRootTokenService;

    @Test
    void init_whenRootExists_returnsEmpty() {
        when(existRootPort.exists()).thenReturn(true);

        assertTrue(initRootTokenService.init().isEmpty());
        assertFalse(initRootTokenService.matches("anything"));
    }

    @Test
    void init_whenNoRoot_generatesTokenThatMatchesUntilConsumed() {
        when(existRootPort.exists()).thenReturn(false);

        var generated = initRootTokenService.init();
        assertTrue(generated.isPresent());
        assertTrue(initRootTokenService.matches(generated.get()));
        assertFalse(initRootTokenService.matches("wrong"));
        assertTrue(initRootTokenService.init().isEmpty());

        initRootTokenService.consume();
        assertFalse(initRootTokenService.matches(generated.get()));
    }

}
