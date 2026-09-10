package dev.hieplp.pastebin.adapter.out.mongo.adapter;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteFileDocument;
import dev.hieplp.pastebin.adapter.out.mongo.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.out.mongo.repository.PasteFileRepository;
import dev.hieplp.pastebin.domain.vo.PasteId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasteFileAdapterTest {

    @Mock
    private PasteFileRepository pasteFileRepo;

    @Mock
    private PasteFileMapper pasteFileMapper;

    @Mock
    private dev.hieplp.pastebin.adapter.out.mongo.mapper.VoMapper voMapper;

    @InjectMocks
    private PasteFileAdapter pasteFileAdapter;

    @Test
    void deleteByPasteIds_whenEmptyOrNull_doesNothing() {
        pasteFileAdapter.deleteByPasteIds(null);
        pasteFileAdapter.deleteByPasteIds(List.of());

        verify(pasteFileRepo, never()).findByPasteIdIn(any());
        verify(pasteFileRepo, never()).deleteAll(any());
    }

    @Test
    void deleteByPasteIds_whenFilesFound_deletesRecords() {
        var file1 = new PasteFileDocument();
        file1.setFileId("f-1");
        file1.setStorageKey("uploads/p-1/f-1.txt");

        var file2 = new PasteFileDocument();
        file2.setFileId("f-2");
        file2.setStorageKey("uploads/p-1/f-2.txt");

        when(pasteFileRepo.findByPasteIdIn(List.of("p-1"))).thenReturn(List.of(file1, file2));
        when(voMapper.toPasteIdStrings(List.of(PasteId.of("p-1")))).thenReturn(List.of("p-1"));

        pasteFileAdapter.deleteByPasteIds(List.of(PasteId.of("p-1")));

        verify(pasteFileRepo).deleteAll(List.of(file1, file2));
    }

    @Test
    void deleteByPasteIds_whenNoFilesFound_doesNothing() {
        when(pasteFileRepo.findByPasteIdIn(List.of("p-1"))).thenReturn(List.of());
        when(voMapper.toPasteIdStrings(List.of(PasteId.of("p-1")))).thenReturn(List.of("p-1"));

        pasteFileAdapter.deleteByPasteIds(List.of(PasteId.of("p-1")));

        verify(pasteFileRepo, never()).deleteAll(any());
    }
}
