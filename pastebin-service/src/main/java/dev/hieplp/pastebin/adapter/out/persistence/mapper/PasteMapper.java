package dev.hieplp.pastebin.adapter.out.persistence.mapper;

import dev.hieplp.pastebin.adapter.out.persistence.entity.PasteEntity;
import dev.hieplp.pastebin.domain.model.Paste;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "PastePersistenceMapperImpl"
)
public interface PasteMapper {

    Paste toModel(PasteEntity entity);

    PasteEntity toEntity(Paste model);

}