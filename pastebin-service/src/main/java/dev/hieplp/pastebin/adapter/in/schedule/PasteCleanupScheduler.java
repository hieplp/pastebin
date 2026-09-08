package dev.hieplp.pastebin.adapter.in.schedule;

import dev.hieplp.pastebin.adapter.in.schedule.config.CleanupProperties;
import dev.hieplp.pastebin.application.dto.paste.command.DeleteExpiredPastesCommand;
import dev.hieplp.pastebin.application.port.in.paste.DeleteExpiredPastesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Slf4j
@Component
@RequiredArgsConstructor
public class PasteCleanupScheduler implements SchedulingConfigurer {

    private final CleanupProperties cleanupProperties;

    private final DeleteExpiredPastesUseCase deleteExpiredPastesUseCase;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(this::deleteExpiredOrInactivePastes, cleanupProperties.cron());
    }

    void deleteExpiredOrInactivePastes() {
        var command = new DeleteExpiredPastesCommand(Instant.now());
        deleteExpiredPastesUseCase.delete(command);
    }

}
