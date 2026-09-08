package dev.hieplp.pastebin.application.port.out.storage;

import dev.hieplp.pastebin.domain.vo.StorageKey;

import java.util.List;

public interface ListStoragePort {

    List<StorageKey> listKeys();

}
