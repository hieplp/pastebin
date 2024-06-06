package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.client.PasswordClient;
import dev.hieplp.pastebin.auth.payload.response.user.PasswordResponse;
import dev.hieplp.pastebin.auth.service.PasswordService;
import dev.hieplp.pastebin.common.feign.FeignUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordClient passwordClient;

    @Override
    public PasswordResponse findByUserId(String userId) {
        log.info("Find password by userId: {}", userId);
        var response = passwordClient.findByUserId(userId);
        return FeignUtil.parseResponse(response);
    }
}
