package dev.hieplp.pastebin.domain.vo;

public record ContentType(String value) {

    public static ContentType of(String value) {
        return new ContentType(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
