package dev.hieplp.pastebin.auth.client;


import dev.hieplp.pastebin.auth.config.OAuthFeignConfig;
import dev.hieplp.pastebin.auth.payload.request.user.CreateUserRequest;
import dev.hieplp.pastebin.auth.payload.response.user.UserResponse;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${service.user.internal-user}",
        configuration = OAuthFeignConfig.class
)
public interface UserClient {
    @PostMapping
    CommonResponse<UserResponse> create(@RequestBody CreateUserRequest request);

    @GetMapping("/username/{username}")
    CommonResponse<UserResponse> findByUsername(@PathVariable String username);

    @GetMapping("/{userId}")
    CommonResponse<UserResponse> findByUserId(@PathVariable String userId);

    @GetMapping("/exists/username/{username}")
    CommonResponse<Boolean> existsByUsername(@PathVariable String username);
}
