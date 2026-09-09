package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.port.in.root.ExistRootUseCase;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExistRootService implements ExistRootUseCase {

    private final ExistRootPort existRootPort;

    @Override
    public boolean exists() {
        log.info("Check if root exists");
        var exists = existRootPort.exists();
        log.info("Root exists={}", exists);
        return exists;
    }

}
