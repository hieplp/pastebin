package dev.hieplp.pastebin.domain.vo;

public record Username(String value) {

    public static Username of(String value) {
        return new Username(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
