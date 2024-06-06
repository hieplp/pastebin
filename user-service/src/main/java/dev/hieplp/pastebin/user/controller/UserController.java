package dev.hieplp.pastebin.user.controller;

import dev.hieplp.pastebin.common.auth.UserInfoDetails;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import dev.hieplp.pastebin.user.payload.response.UserResponse;
import dev.hieplp.pastebin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public CommonResponse<UserResponse> getProfile(@AuthenticationPrincipal UserInfoDetails userDetails) {
        log.info("Get profile");
        var profile = userService.getProfile(userDetails.getUserId());
        return CommonResponse.success(profile);
    }
}
