package dev.hieplp.pastebin.domain.vo;

public record FileName(String value) {

    public static FileName of(String value) {
        return new FileName(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
