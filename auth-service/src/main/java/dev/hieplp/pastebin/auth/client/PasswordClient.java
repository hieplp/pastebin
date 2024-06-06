package dev.hieplp.pastebin.auth.client;


import dev.hieplp.pastebin.auth.config.OAuthFeignConfig;
import dev.hieplp.pastebin.auth.payload.response.user.PasswordResponse;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "${service.user.internal-password}",
        configuration = OAuthFeignConfig.class
)
public interface PasswordClient {
    @GetMapping("{userId}")
    CommonResponse<PasswordResponse> findByUserId(@PathVariable String userId);
}
