package dev.hieplp.pastebin.adapter.in.web.exception;

import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.domain.exception.BadRequestException;
import dev.hieplp.pastebin.domain.exception.NotFoundException;
import dev.hieplp.pastebin.domain.exception.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<Void> handleNotFound(NotFoundException e) {
        return BaseResponse.of("404", e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<Void> handleUnauthorized(UnauthorizedException e) {
        return BaseResponse.of("401", e.getMessage(), null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleValidation(MethodArgumentNotValidException e) {
        var message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return BaseResponse.of("400", message.isEmpty() ? "Validation failed" : message, null);
    }

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleBadRequest(RuntimeException e) {
        return BaseResponse.of("400", e.getMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<Void> handleDataIntegrity(DataIntegrityViolationException e) {
        return BaseResponse.of("409", "Data integrity violation: alias may already be in use", null);
    }

}
