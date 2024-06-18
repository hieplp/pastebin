package dev.hieplp.pastebin.auth.config;

import dev.hieplp.pastebin.common.exceptionhandler.CommonExceptionHandler;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends CommonExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse<?>> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.ok(CommonResponse.unauthorized());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.ok(CommonResponse.unauthorized());
    }
}
