package dev.hieplp.pastebin.auth.service.impl;

import dev.hieplp.pastebin.auth.client.UserClient;
import dev.hieplp.pastebin.auth.payload.request.user.CreateUserRequest;
import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;
import dev.hieplp.pastebin.auth.service.UserService;
import dev.hieplp.pastebin.common.feign.FeignUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public UserResponse create(CreateUserRequest request) {
        log.info("Create user with request: {}", request);
        var response = userClient.create(request);
        return FeignUtil.parseResponse(response);
    }

    @Override
    public UserResponse findByUsername(String username) {
        log.info("Find user by username: {}", username);
        var response = userClient.findByUsername(username);
        return FeignUtil.parseResponse(response);
    }

    @Override
    public UserResponse findByUserId(String userId) {
        log.info("Find user by userId: {}", userId);
        var response = userClient.findByUserId(userId);
        return FeignUtil.parseResponse(response);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Check if username exists: {} in user-service", username);
        var response = userClient.existsByUsername(username);
        return FeignUtil.parseResponse(response);
    }
}
