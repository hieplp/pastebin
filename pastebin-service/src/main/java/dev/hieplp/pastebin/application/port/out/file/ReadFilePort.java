package dev.hieplp.pastebin.application.port.out.file;

public interface ReadFilePort {

    byte[] read(String storageKey);

}
