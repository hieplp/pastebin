package dev.hieplp.pastebin.application.port.out.storage;

import dev.hieplp.pastebin.domain.vo.StorageKey;

public interface DeleteStoragePort {

    void delete(StorageKey storageKey);

}
