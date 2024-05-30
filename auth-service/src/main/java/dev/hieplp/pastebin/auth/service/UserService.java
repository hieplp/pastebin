package dev.hieplp.pastebin.auth.service;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import dev.hieplp.pastebin.auth.payload.response.UserResponse;

public interface UserService {
    /**
     * Save user
     *
     * @param user User entity
     * @return Saved user entity
     */
    UserEntity save(UserEntity user);

    /**
     * Find user by username
     *
     * @param username Username
     * @return User entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException if user not found
     */
    UserEntity findByUsername(String username);

    /**
     * Find user by userId
     *
     * @param userId UserId
     * @return User entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException if user not found
     */
    UserEntity findByUserId(String userId);

    /**
     * Check if username exists
     *
     * @param username Username
     * @return True if username exists, otherwise false
     */
    boolean existsByUsername(String username);

    /**
     * Get profile of user
     *
     * @param userId User id
     * @return User response
     */
    UserResponse getProfile(String userId);
}
