package dev.hieplp.pastebin.domain.vo;

public record Actor(
        String id
) {

    public static Actor of(String id) {
        return new Actor(id);
    }

    public static Actor anonymous() {
        return of("ANONYMOUS");
    }

    public static Actor system() {
        return of("SYSTEM");
    }

    @Override
    public String toString() {
        return id;
    }
}
