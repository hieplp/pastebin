package dev.hieplp.pastebin.application.service.file;

import dev.hieplp.pastebin.application.port.in.file.DeleteOrphanFilesUseCase;
import dev.hieplp.pastebin.application.port.out.file.GetFilePort;
import dev.hieplp.pastebin.application.port.out.storage.DeleteStoragePort;
import dev.hieplp.pastebin.application.port.out.storage.ListStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteOrphanFilesService implements DeleteOrphanFilesUseCase {

    private final GetFilePort getFilePort;

    private final ListStoragePort listStoragePort;
    private final DeleteStoragePort deleteStoragePort;

    @Override
    public void delete() {
        log.info("Delete orphan files");

        // ponytail: full key lists in heap; page/stream if file count hits hundreds of thousands
        var storedKeys = listStoragePort.listKeys();
        if (storedKeys.isEmpty()) {
            log.info("No files found in storage to check for orphans");
            return;
        }

        var knownKeys = new HashSet<>(getFilePort.findAllStorageKeys());
        // ponytail: races with an in-flight upload (file written, DB row not committed yet);
        // a weekly run makes that window negligible.
        var orphans = storedKeys.stream()
                .filter(key -> !knownKeys.contains(key))
                .toList();

        if (orphans.isEmpty()) {
            log.info("No orphan files found in storage (checked {} files)", storedKeys.size());
            return;
        }

        // ponytail: sequential single-object deletes; s3.deleteObjects (1000/req) if orphan volume hurts
        orphans.forEach(deleteStoragePort::delete);
        log.info("Deleted {} orphan files not present in database", orphans.size());
    }

}
