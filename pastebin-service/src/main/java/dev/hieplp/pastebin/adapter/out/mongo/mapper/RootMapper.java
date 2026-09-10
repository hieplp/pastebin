package dev.hieplp.pastebin.adapter.out.mongo.mapper;

import dev.hieplp.pastebin.adapter.out.mongo.document.RootDocument;
import dev.hieplp.pastebin.domain.model.Root;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "RootMongoMapperImpl"
)
public interface RootMapper {

    Root toModel(RootDocument document);

    RootDocument toDocument(Root model);

}
