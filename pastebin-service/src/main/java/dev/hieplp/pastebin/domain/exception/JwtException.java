package dev.hieplp.pastebin.domain.exception;

public class JwtException extends BaseException {

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
