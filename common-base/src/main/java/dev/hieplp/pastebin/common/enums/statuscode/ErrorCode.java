package dev.hieplp.pastebin.common.enums.statuscode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode implements ResponseCode {
    BAD_REQUEST("4000", "Bad request"),
    UNAUTHORIZED("4001", "Unauthorized"),
    FORBIDDEN("4003", "Forbidden"),
    NOT_FOUND("4004", "Not found"),
    DUPLICATED("4005", "Duplicated"),
    INTERNAL_SERVER_ERROR("5000", "Internal server error"),

    ;

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}