package dev.hieplp.pastebin.domain.vo;

import java.util.UUID;

public record FileId(String value) {

    public static FileId of(String value) {
        return new FileId(value);
    }

    public static FileId uuid() {
        return of(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }

}
