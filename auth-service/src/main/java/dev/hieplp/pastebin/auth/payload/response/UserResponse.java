package dev.hieplp.pastebin.auth.payload.response;

import dev.hieplp.pastebin.auth.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String username;
    private String name;

    public UserResponse(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.username = userEntity.getUsername();
        this.name = userEntity.getName();
    }
}
