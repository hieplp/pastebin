package dev.hieplp.pastebin.domain.vo;

import java.util.UUID;

public record RootId(String value) {

    public static RootId of(String value) {
        return new RootId(value);
    }

    public static RootId uuid() {
        return of(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }

}
