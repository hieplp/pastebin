package dev.hieplp.pastebin.common.enums.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenClaimKey {
    TYPE("type"),
    USER_ID("userId"),
    ;

    private final String key;
}
