package dev.hieplp.pastebin.common.payload.response;


import dev.hieplp.pastebin.common.enums.statuscode.ErrorCode;
import dev.hieplp.pastebin.common.enums.statuscode.SuccessCode;

public record CommonResponse<T>(
        String code,
        String message,
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(SuccessCode.SUCCESS.getCode(), SuccessCode.SUCCESS.getMessage(), data);
    }

    public static CommonResponse<Object> notFound() {
        return new CommonResponse<>(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMessage(), null);
    }

    public static CommonResponse<Object> badRequest() {
        return new CommonResponse<>(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage(), null);
    }

    public static CommonResponse<Object> unauthorized() {
        return new CommonResponse<>(ErrorCode.UNAUTHORIZED.getCode(), ErrorCode.UNAUTHORIZED.getMessage(), null);
    }

    public static CommonResponse<Object> duplicated() {
        return new CommonResponse<>(ErrorCode.DUPLICATED.getCode(), ErrorCode.DUPLICATED.getMessage(), null);
    }

    public static CommonResponse<Object> forbidden() {
        return new CommonResponse<>(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getMessage(), null);
    }

    public static CommonResponse<Object> internalServerError() {
        return new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), null);
    }
}
