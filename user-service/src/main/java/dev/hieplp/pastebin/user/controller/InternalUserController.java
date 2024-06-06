package dev.hieplp.pastebin.user.controller;

import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import dev.hieplp.pastebin.user.payload.request.CreateUserRequest;
import dev.hieplp.pastebin.user.payload.response.UserResponse;
import dev.hieplp.pastebin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @PostMapping
    public CommonResponse<UserResponse> create(@RequestBody CreateUserRequest request) {
        log.debug("Create user with request: {}", request);
        final var response = userService.create(request);
        return CommonResponse.success(response);
    }

    @PostMapping("{userId}")
    public CommonResponse<UserResponse> findByUserId(@PathVariable String userId) {
        log.debug("Find user by userId: {}", userId);
        final var response = userService.findByUserId(userId);
        return CommonResponse.success(response);
    }

    @GetMapping("/username/{username}")
    public CommonResponse<UserResponse> findByUsername(@PathVariable String username) {
        log.debug("Find user by username: {}", username);
        final var response = userService.findByUsername(username);
        return CommonResponse.success(response);
    }

    @GetMapping("/exists/username/{username}")
    public CommonResponse<Boolean> existsByUsername(@PathVariable String username) {
        log.debug("Check if username exists: {}", username);
        final var response = userService.existsByUsername(username);
        return CommonResponse.success(response);
    }
}
