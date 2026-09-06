package dev.hieplp.pastebin.adapter.in.web.payload.common;

public record BaseResponse<T>(
        String code,
        String message,
        T data
) {

    public static <T> BaseResponse<T> of(
            String code,
            String message,
            T data
    ) {
        return new BaseResponse<>(code, message, data);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return of("2000", "OK", data);
    }

}
