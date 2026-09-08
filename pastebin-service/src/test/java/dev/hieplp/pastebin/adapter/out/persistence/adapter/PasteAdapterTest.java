package dev.hieplp.pastebin.adapter.out.persistence.adapter;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteEntity;
import dev.hieplp.pastebin.adapter.out.persistence.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.persistence.repository.PasteRepository;
import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.vo.PasteId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasteAdapterTest {

    @Mock
    private PasteRepository pasteRepo;

    @Mock
    private PasteMapper pasteMapper;

    @Mock
    private dev.hieplp.pastebin.adapter.out.persistence.mapper.VoMapper voMapper;

    @InjectMocks
    private PasteAdapter pasteAdapter;

    @Test
    void findExpiredOrInactive_mapsToPastes() {
        var entity = new PasteEntity();
        entity.setPasteId("p-1");
        var model = new dev.hieplp.pastebin.domain.model.Paste();
        model.setPasteId(PasteId.of("p-1"));

        when(pasteRepo.findByStatusOrExpiredAtLessThanEqual(eq(PasteStatus.INACTIVE), any(Instant.class)))
                .thenReturn(List.of(entity));
        when(pasteMapper.toModels(List.of(entity))).thenReturn(List.of(model));

        var result = pasteAdapter.findExpiredOrInactive(Instant.now());

        assertEquals(List.of(model), result);
    }

    @Test
    void deleteAll_whenPastesPresent_deletesInBatch() {
        var pasteIds = List.of(PasteId.of("p-1"), PasteId.of("p-2"));
        when(voMapper.toPasteIdStrings(pasteIds)).thenReturn(List.of("p-1", "p-2"));

        pasteAdapter.deleteAll(pasteIds);

        verify(pasteRepo).deleteAllByIdInBatch(List.of("p-1", "p-2"));
    }

    @Test
    void deleteAll_whenNullOrEmpty_doesNothing() {
        pasteAdapter.deleteAll(null);
        pasteAdapter.deleteAll(List.of());

        verify(pasteRepo, never()).deleteAllByIdInBatch(any());
    }

    @Test
    void deleteById_whenPasteIdPresent_deletesFromRepo() {
        var pasteId = PasteId.of("p-1");

        pasteAdapter.deleteById(pasteId);

        verify(pasteRepo).deleteById("p-1");
    }

    @Test
    void deleteById_whenNull_doesNothing() {
        pasteAdapter.deleteById(null);

        verify(pasteRepo, never()).deleteById(any());
    }
}
