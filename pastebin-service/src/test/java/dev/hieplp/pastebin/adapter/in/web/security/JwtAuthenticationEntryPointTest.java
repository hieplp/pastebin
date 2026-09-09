package dev.hieplp.pastebin.adapter.in.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAuthenticationEntryPointTest {

    @Test
    void commence_writesJsonUnauthorized() throws Exception {
        var response = new MockHttpServletResponse();

        new JwtAuthenticationEntryPoint().commence(
                new MockHttpServletRequest(),
                response,
                new InsufficientAuthenticationException("missing")
        );

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        assertEquals("{\"code\":\"401\",\"message\":\"Unauthorized\",\"data\":null}", response.getContentAsString());
    }

}
