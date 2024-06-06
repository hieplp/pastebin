package dev.hieplp.pastebin.paste.client;

import dev.hieplp.pastebin.paste.config.OAuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "${service.user.name}",
        path = "${service.user.path}",
        configuration = OAuthFeignConfig.class
)
public interface UserClient {
}
