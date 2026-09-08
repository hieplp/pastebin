package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.application.dto.file.command.CreateFileCommand;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class
)
public interface MultipartFileMapper {

    @Mapping(target = "name", source = "originalFilename")
    @Mapping(target = "contentType", source = "contentType")
    @Mapping(target = "content", expression = "java(file.getBytes())")
    CreateFileCommand toCommand(MultipartFile file) throws IOException;

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<CreateFileCommand> toCommands(List<MultipartFile> files) throws IOException;

}
