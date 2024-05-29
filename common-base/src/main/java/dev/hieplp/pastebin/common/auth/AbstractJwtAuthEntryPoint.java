package dev.hieplp.pastebin.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public abstract class AbstractJwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        log.warn("Unauthorized request: {} with message: {}", request.getRequestURI(), e.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        var responseStream = response.getOutputStream();

        var mapper = new ObjectMapper();
        var data = CommonResponse.unauthorized();
        mapper.writeValue(responseStream, data);

        responseStream.flush();
    }
}
