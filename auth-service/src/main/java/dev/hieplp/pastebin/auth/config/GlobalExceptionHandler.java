package dev.hieplp.pastebin.auth.config;

import dev.hieplp.pastebin.common.exceptionhandler.CommonExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends CommonExceptionHandler {
}
