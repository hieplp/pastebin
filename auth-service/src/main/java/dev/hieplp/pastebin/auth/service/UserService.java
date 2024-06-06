package dev.hieplp.pastebin.auth.service;


import dev.hieplp.pastebin.auth.payload.request.user.CreateUserRequest;
import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;

public interface UserService {

    /**
     * Call user-service to create a new user
     *
     * @param request Create user request
     * @return User response
     */
    UserResponse create(CreateUserRequest request);

    /**
     * Call user-service to find user by username
     *
     * @param username username
     * @return User response
     */
    UserResponse findByUsername(String username);

    /**
     * Call user-service to find user by user id
     *
     * @param userId id of user
     * @return User response
     */
    UserResponse findByUserId(String userId);

    /**
     * Call user-service to check if user exists by username
     *
     * @param username username
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);

}
