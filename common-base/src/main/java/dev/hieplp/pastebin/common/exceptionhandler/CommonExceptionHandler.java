package dev.hieplp.pastebin.common.exceptionhandler;


import dev.hieplp.pastebin.common.exception.BadRequestException;
import dev.hieplp.pastebin.common.exception.DuplicateException;
import dev.hieplp.pastebin.common.exception.NotFoundException;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@Slf4j
@Order
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<?>> handleBadRequestException(BadRequestException e) {
        log.error("Bad request exception: {}", e.getMessage());
        return ResponseEntity.ok(CommonResponse.badRequest());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied exception: {}", e.getMessage());
        return ResponseEntity.ok(CommonResponse.forbidden());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleNotFoundException(NotFoundException e) {
        log.error("Not found exception: {}", e.getMessage());
        return ResponseEntity.ok(CommonResponse.notFound());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<CommonResponse<?>> handleDuplicatedException(DuplicateException e) {
        log.error("Duplicated exception: {}", e.getMessage());
        return ResponseEntity.ok(CommonResponse.duplicated());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception e) {
        log.error("Internal server error: ", e);
        return ResponseEntity.ok(CommonResponse.internalServerError());
    }
}
