package dev.hieplp.pastebin.adapter.out.localstorage.adapter;

import dev.hieplp.pastebin.adapter.out.localstorage.config.LocalStorageProperties;
import dev.hieplp.pastebin.domain.vo.StorageKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalStorageAdapterTest {

    @TempDir
    Path tempDir;

    @Test
    void delete_existingFile_deletesFile() throws IOException {
        var props = new LocalStorageProperties(tempDir.toString());
        var adapter = new LocalStorageAdapter(props);

        var file = tempDir.resolve("test.txt");
        Files.writeString(file, "content");
        assertTrue(Files.exists(file));

        adapter.delete(StorageKey.of("test.txt"));
        assertFalse(Files.exists(file));
    }

    @Test
    void delete_nonExistentOrNull_doesNotThrow() {
        var props = new LocalStorageProperties(tempDir.toString());
        var adapter = new LocalStorageAdapter(props);

        adapter.delete(StorageKey.of("nonexistent.txt"));
        adapter.delete(null);
        adapter.delete(StorageKey.of(""));
    }

    @Test
    void listKeys_skipsDotfilesAndUsesForwardSlashes() throws IOException {
        Files.createDirectories(tempDir.resolve("folder"));
        Files.writeString(tempDir.resolve("folder").resolve("file.txt"), "x");
        Files.writeString(tempDir.resolve(".DS_Store"), "x");
        Files.writeString(tempDir.resolve(".gitkeep"), "x");

        var adapter = new LocalStorageAdapter(new LocalStorageProperties(tempDir.toString()));

        assertEquals(List.of(StorageKey.of("folder/file.txt")), adapter.listKeys());
    }
}
