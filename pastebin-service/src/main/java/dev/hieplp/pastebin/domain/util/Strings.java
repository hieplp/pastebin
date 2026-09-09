package dev.hieplp.pastebin.domain.util;

import java.util.Optional;

public final class Strings {

    private Strings() {
    }

    public static Optional<String> trim(String s) {
        return s == null || s.isBlank() ? Optional.empty() : Optional.of(s.trim());
    }
}
