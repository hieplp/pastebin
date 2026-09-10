package dev.hieplp.pastebin.adapter.out.mongo.mapper;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteFileDocument;
import dev.hieplp.pastebin.domain.model.PasteFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "PasteFileMongoMapperImpl"
)
public interface PasteFileMapper {

    @Mapping(target = "content", ignore = true)
    PasteFile toModel(PasteFileDocument document);

    PasteFileDocument toDocument(PasteFile model);

    List<PasteFile> toModels(List<PasteFileDocument> documents);

    List<PasteFileDocument> toDocuments(List<PasteFile> models);

}
