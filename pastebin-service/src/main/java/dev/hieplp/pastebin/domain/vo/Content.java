package dev.hieplp.pastebin.domain.vo;

public record Content(String value) {

    public Content {
        value = value == null ? "" : value.trim();
    }

    public static Content of(String value) {
        return new Content(value);
    }

    public boolean isBlank() {
        return value.isBlank();
    }

    @Override
    public String toString() {
        return value;
    }

}
