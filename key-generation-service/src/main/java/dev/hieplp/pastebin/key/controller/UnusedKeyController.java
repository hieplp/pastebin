package dev.hieplp.pastebin.key.controller;

import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import dev.hieplp.pastebin.key.service.UnusedKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/unused-key")
@RequiredArgsConstructor
public class UnusedKeyController {

    private final UnusedKeyService unusedKeyService;

    @GetMapping
    public CommonResponse<?> getUnusedKey() {
        log.debug("Get unused key");
        var response = unusedKeyService.getUnusedKey();
        return CommonResponse.success(response);
    }
}
