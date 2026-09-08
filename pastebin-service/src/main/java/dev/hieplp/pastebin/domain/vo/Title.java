package dev.hieplp.pastebin.domain.vo;

public record Title(String value) {

    public static Title of(String value) {
        return new Title(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
