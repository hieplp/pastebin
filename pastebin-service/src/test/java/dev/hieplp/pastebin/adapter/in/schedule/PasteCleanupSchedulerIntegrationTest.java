package dev.hieplp.pastebin.adapter.in.schedule;

import dev.hieplp.pastebin.adapter.out.jpa.entity.PasteEntity;
import dev.hieplp.pastebin.adapter.out.jpa.repository.PasteRepository;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PasteCleanupSchedulerIntegrationTest {

    @Autowired
    private PasteCleanupScheduler pasteCleanupScheduler;

    @Autowired
    private PasteRepository pasteRepository;

    @AfterEach
    void tearDown() {
        pasteRepository.deleteAll();
    }

    @Test
    void deleteExpiredOrInactivePastes_deletesOnlyExpiredOrInactive() {
        var now = Instant.now();

        // 1. Active and not expired -> keep
        var activeFuture = createPaste("p-active-future", PasteStatus.ACTIVE, now.plus(1, ChronoUnit.HOURS));
        // 2. Active with no expiration -> keep
        var activeNoExpiry = createPaste("p-active-no-exp", PasteStatus.ACTIVE, null);
        // 3. Active but expired in past -> delete
        var activeExpired = createPaste("p-active-expired", PasteStatus.ACTIVE, now.minus(1, ChronoUnit.HOURS));
        // 4. Inactive -> delete
        var inactive = createPaste("p-inactive", PasteStatus.INACTIVE, null);

        pasteRepository.save(activeFuture);
        pasteRepository.save(activeNoExpiry);
        pasteRepository.save(activeExpired);
        pasteRepository.save(inactive);

        pasteCleanupScheduler.deleteExpiredOrInactivePastes();

        assertTrue(pasteRepository.existsById("p-active-future"));
        assertTrue(pasteRepository.existsById("p-active-no-exp"));
        assertFalse(pasteRepository.existsById("p-active-expired"));
        assertFalse(pasteRepository.existsById("p-inactive"));
    }

    private PasteEntity createPaste(String id, PasteStatus status, Instant expiredAt) {
        var entity = new PasteEntity();
        entity.setPasteId(id);
        entity.setTitle("Test Title " + id);
        entity.setContent("Test Content");
        entity.setPrivacy(Privacy.PUBLIC);
        entity.setSyntax(Syntax.PLAINTEXT);
        entity.setStatus(status);
        entity.setExpiredAt(expiredAt);
        entity.setBurnAfterRead(false);
        entity.setCreatedBy("tester");
        entity.setCreatedAt(Instant.now());
        return entity;
    }
}
