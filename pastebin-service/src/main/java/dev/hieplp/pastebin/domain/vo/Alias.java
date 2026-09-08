package dev.hieplp.pastebin.domain.vo;

public record Alias(String value) {

    public static Alias of(String value) {
        return new Alias(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
