package dev.hieplp.pastebin.application.port.in.root;

import java.util.Optional;

public interface InitRootTokenUseCase {

    Optional<String> init();

    boolean matches(String token);

    void consume();

}
