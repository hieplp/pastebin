package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteResponse;
import dev.hieplp.pastebin.application.dto.file.command.CreateFileCommand;
import dev.hieplp.pastebin.application.dto.paste.command.CreatePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.CreatePasteResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "PasteWebMapperImpl"
)
public interface PasteMapper {

    @Mapping(target = "files", source = "files")
    CreatePasteCommand toCommand(CreatePasteRequest request, List<CreateFileCommand> files);

    CreatePasteResponse toResponse(CreatePasteResult result);

}
