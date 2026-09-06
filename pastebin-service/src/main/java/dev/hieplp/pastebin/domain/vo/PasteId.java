package dev.hieplp.pastebin.domain.vo;

import java.util.UUID;

public record PasteId(String value) {

    public static PasteId of(String value) {
        return new PasteId(value);
    }

    public static PasteId uuid() {
        return of(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }

}
