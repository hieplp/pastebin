package dev.hieplp.pastebin.domain.vo;

public record StorageKey(String value) {

    public static StorageKey of(String value) {
        return new StorageKey(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
