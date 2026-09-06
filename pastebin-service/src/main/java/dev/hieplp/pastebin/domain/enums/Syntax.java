package dev.hieplp.pastebin.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Syntax {
    JAVASCRIPT("js"),
    TYPESCRIPT("ts"),
    PYTHON("py"),
    JSON("json"),
    MARKDOWN("md"),
    HTML("html"),
    CSS("css"),
    SQL("sql"),
    RUST("rs"),
    GO("go"),
    BASH("sh"),
    PLAINTEXT("txt");

    private final String extension;

}
