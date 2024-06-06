package dev.hieplp.pastebin.user.controller;


import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import dev.hieplp.pastebin.user.payload.response.PasswordResponse;
import dev.hieplp.pastebin.user.service.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/password")
@RequiredArgsConstructor
public class InternalPasswordController {

    private final PasswordService passwordService;

    @GetMapping("{userId}")
    public CommonResponse<PasswordResponse> findByUserId(@PathVariable String userId) {
        log.debug("Find password by userId: {}", userId);
        var response = passwordService.findByUserId(userId);
        return CommonResponse.success(response);
    }
}
