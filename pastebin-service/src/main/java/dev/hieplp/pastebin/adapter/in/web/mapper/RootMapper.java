package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.adapter.in.web.payload.root.CreateRootRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.root.CreateRootResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.root.LoginRootRequest;
import dev.hieplp.pastebin.application.dto.root.command.CreateRootCommand;
import dev.hieplp.pastebin.application.dto.root.command.LoginRootCommand;
import dev.hieplp.pastebin.application.dto.root.result.CreateRootResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "RootWebMapperImpl"
)
public interface RootMapper {

    CreateRootCommand toCommand(CreateRootRequest request);

    CreateRootResponse toResponse(CreateRootResult result);

    LoginRootCommand toCommand(LoginRootRequest request);


}
