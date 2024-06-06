package dev.hieplp.pastebin.user.service.impl;


import dev.hieplp.pastebin.user.entity.UserEntity;
import dev.hieplp.pastebin.user.payload.request.CreateUserRequest;
import dev.hieplp.pastebin.user.payload.response.UserResponse;
import dev.hieplp.pastebin.user.service.PasswordService;
import dev.hieplp.pastebin.user.service.UserService;
import dev.hieplp.pastebin.user.store.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStore userStore;

    private final PasswordService passwordService;

    @Override
    public UserEntity save(UserEntity user) {
        log.info("Save user: {}", user);
        return userStore.save(user);
    }

    @Override
    public UserResponse findByUsername(String username) {
        log.info("Find user by username: {}", username);
        var entity = userStore.findByUsername(username);
        return new UserResponse(entity);
    }

    @Override
    public UserResponse findByUserId(String userId) {
        log.info("Find user by userId: {}", userId);
        var entity = userStore.findByUserId(userId);
        return new UserResponse(entity);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.info("Check if username exists: {} in database", username);
        return userStore.existsByUsername(username);
    }

    @Override
    public UserResponse getProfile(String userId) {
        log.info("Get profile of user: {}", userId);
        var userEntity = userStore.findByUserId(userId);
        return new UserResponse(userEntity);
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        log.info("Create user with request: {}", request);

        // Password
        var password = passwordService.generatePassword(request.password());

        // User
        var user = UserEntity.builder()
                .username(request.username())
                .name(request.name())
                .build();

        // Bidirectional
        user.setPassword(password);
        password.setUser(user);

        // Save
        user = userStore.save(user);

        return new UserResponse(user);
    }
}
