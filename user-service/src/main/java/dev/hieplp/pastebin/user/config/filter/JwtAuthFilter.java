package dev.hieplp.pastebin.user.config.filter;

import dev.hieplp.pastebin.common.auth.AbstractJwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends AbstractJwtAuthFilter {

}