package dev.hieplp.pastebin.adapter.in.bootstrap;

import dev.hieplp.pastebin.application.port.in.root.InitRootTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootInitRunner implements ApplicationRunner {

    private final InitRootTokenUseCase initRootTokenUseCase;

    @Override
    public void run(ApplicationArguments args) {
        initRootTokenUseCase.init()
                .ifPresent(token -> log.warn("Root init token: {}", token));
    }

}
