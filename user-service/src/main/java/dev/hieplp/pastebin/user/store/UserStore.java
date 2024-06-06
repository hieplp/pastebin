package dev.hieplp.pastebin.user.store;


import dev.hieplp.pastebin.user.entity.UserEntity;

public interface UserStore {
    /**
     * Save user entity
     *
     * @param user User entity
     * @return Saved user entity
     */
    UserEntity save(UserEntity user);

    /**
     * Check if username exists
     *
     * @param username Username
     * @return True if username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Find user entity by username
     *
     * @param username Username
     * @return User entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException If user entity not found
     */
    UserEntity findByUsername(String username);

    /**
     * Find user entity by user id
     *
     * @param userId User id
     * @return User entity
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException If user entity not found
     */
    UserEntity findByUserId(String userId);
}