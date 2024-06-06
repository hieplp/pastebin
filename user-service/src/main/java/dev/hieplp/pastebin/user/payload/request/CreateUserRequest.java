package dev.hieplp.pastebin.user.payload.request;

public record CreateUserRequest(
        String username,
        String name,
        String password
) {

}
