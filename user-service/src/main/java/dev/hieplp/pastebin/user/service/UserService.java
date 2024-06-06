package dev.hieplp.pastebin.user.service;


import dev.hieplp.pastebin.user.payload.request.CreateUserRequest;
import dev.hieplp.pastebin.user.payload.response.UserResponse;

public interface UserService {
    /**
     * Find user by username
     *
     * @param username Username
     * @return User response
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException if user not found
     */
    UserResponse findByUsername(String username);

    /**
     * Find user by userId
     *
     * @param userId UserId
     * @return User response
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException if user not found
     */
    UserResponse findByUserId(String userId);

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

    /**
     * Create user
     *
     * @param request Create user request
     * @return User entity
     */
    UserResponse create(CreateUserRequest request);
}
