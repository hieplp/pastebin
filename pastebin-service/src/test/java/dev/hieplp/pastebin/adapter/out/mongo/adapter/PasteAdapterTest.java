package dev.hieplp.pastebin.adapter.out.mongo.adapter;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteDocument;
import dev.hieplp.pastebin.adapter.out.mongo.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.out.mongo.repository.PasteRepository;
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
    private dev.hieplp.pastebin.adapter.out.mongo.mapper.VoMapper voMapper;

    @InjectMocks
    private PasteAdapter pasteAdapter;

    @Test
    void findExpiredOrInactive_mapsToPastes() {
        var document = new PasteDocument();
        document.setPasteId("p-1");
        var model = new dev.hieplp.pastebin.domain.model.Paste();
        model.setPasteId(PasteId.of("p-1"));

        when(pasteRepo.findByStatusOrExpiredAtLessThanEqual(eq(PasteStatus.INACTIVE), any(Instant.class)))
                .thenReturn(List.of(document));
        when(pasteMapper.toModels(List.of(document))).thenReturn(List.of(model));

        var result = pasteAdapter.findExpiredOrInactive(Instant.now());

        assertEquals(List.of(model), result);
    }

    @Test
    void deleteAll_whenPastesPresent_deletesById() {
        var pasteIds = List.of(PasteId.of("p-1"), PasteId.of("p-2"));
        when(voMapper.toPasteIdStrings(pasteIds)).thenReturn(List.of("p-1", "p-2"));

        pasteAdapter.deleteAll(pasteIds);

        verify(pasteRepo).deleteAllById(List.of("p-1", "p-2"));
    }

    @Test
    void deleteAll_whenNullOrEmpty_doesNothing() {
        pasteAdapter.deleteAll(null);
        pasteAdapter.deleteAll(List.of());

        verify(pasteRepo, never()).deleteAllById(any());
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
