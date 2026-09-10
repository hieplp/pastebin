package dev.hieplp.pastebin.adapter.out.mongo.mapper;

import dev.hieplp.pastebin.adapter.out.mongo.document.PasteDocument;
import dev.hieplp.pastebin.domain.model.Paste;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "PasteMongoMapperImpl"
)
public interface PasteMapper {

    Paste toModel(PasteDocument document);

    List<Paste> toModels(List<PasteDocument> documents);

    PasteDocument toDocument(Paste model);

    List<PasteDocument> toDocuments(List<Paste> models);

}
