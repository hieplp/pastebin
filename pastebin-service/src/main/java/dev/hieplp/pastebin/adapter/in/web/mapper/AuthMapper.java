package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.adapter.in.web.payload.auth.LoginResponse;
import dev.hieplp.pastebin.application.dto.auth.LoginResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "AuthWebMapperImpl"
)
public interface AuthMapper {

    LoginResponse toResponse(LoginResult result);

}
