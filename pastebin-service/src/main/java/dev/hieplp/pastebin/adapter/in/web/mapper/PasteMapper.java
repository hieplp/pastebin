package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteResponse;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "PasteWebMapperImpl"
)
public interface PasteMapper {

    CreatePasteCommand toCommand(CreatePasteRequest request);

    CreatePasteResponse toResponse(CreatePasteResult result);
}
