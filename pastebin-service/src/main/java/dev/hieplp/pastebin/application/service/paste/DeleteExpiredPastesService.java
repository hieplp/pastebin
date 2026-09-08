package dev.hieplp.pastebin.application.service.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeleteExpiredPastesResult;
import dev.hieplp.pastebin.application.port.in.paste.DeleteExpiredPastesUseCase;
import dev.hieplp.pastebin.application.port.in.paste.DeletePasteUseCase;
import dev.hieplp.pastebin.application.port.out.paste.GetPastePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteExpiredPastesService implements DeleteExpiredPastesUseCase {

    private final GetPastePort getPastePort;
    private final DeletePasteUseCase deletePasteUseCase;

    @Transactional
    @Override
    public DeleteExpiredPastesResult delete(DeleteExpiredPastesCommand command) {
        var time = command != null && command.time() != null ? command.time() : Instant.now();
        log.info("Running scheduled cleanup for expired or inactive pastes before {}", time);

        var expiredPastes = getPastePort.findExpiredOrInactive(time);
        if (expiredPastes.isEmpty()) {
            log.info("No expired or inactive pastes found to delete");
            return new DeleteExpiredPastesResult(0);
        }

        for (var paste : expiredPastes) {
            deletePasteUseCase.delete(paste.getPasteId());
        }

        log.info("Deleted {} expired or inactive pastes", expiredPastes.size());
        return new DeleteExpiredPastesResult(expiredPastes.size());
    }

}
