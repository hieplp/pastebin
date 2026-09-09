package dev.hieplp.pastebin.application.service.root;

import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.root.command.CreateRootCommand;
import dev.hieplp.pastebin.application.dto.root.result.CreateRootResult;
import dev.hieplp.pastebin.application.port.in.root.CreateRootUseCase;
import dev.hieplp.pastebin.application.port.in.root.InitRootTokenUseCase;
import dev.hieplp.pastebin.application.port.out.password.HashPasswordPort;
import dev.hieplp.pastebin.application.port.out.root.ExistRootPort;
import dev.hieplp.pastebin.application.port.out.root.SaveRootPort;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import dev.hieplp.pastebin.domain.model.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateRootService implements CreateRootUseCase {

    private final ExistRootPort existRootPort;
    private final SaveRootPort saveRootPort;

    private final HashPasswordPort hashPasswordPort;

    private final InitRootTokenUseCase initRootTokenUseCase;

    @Transactional
    @Override
    public CreateRootResult create(CommandEnvelope<CreateRootCommand> envelope) {
        var command = envelope.command();
        var actor = envelope.actor();
        log.info("Create root with username={} by actor={}", command.username(), actor);

        if (!initRootTokenUseCase.matches(command.token())) {
            throw new UnauthorizedException("Invalid init token");
        }

        if (existRootPort.exists()) {
            throw new BadRequestException("Root already exists");
        }

        var saved = saveRootPort.save(Root.create(
                command.username(),
                hashPasswordPort.hash(command.password()),
                actor
        ));
        log.info("Root is saved with rootId={}", saved.getRootId());

        initRootTokenUseCase.consume();

        return CreateRootResult.from(saved);
    }

}
