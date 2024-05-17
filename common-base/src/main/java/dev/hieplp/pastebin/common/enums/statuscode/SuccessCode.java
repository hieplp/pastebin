package dev.hieplp.pastebin.common.enums.statuscode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode implements ResponseCode {
    SUCCESS("2000", "Success");

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