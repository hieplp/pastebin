package dev.hieplp.pastebin.adapter.out.jpa.mapper;

import dev.hieplp.pastebin.adapter.out.jpa.entity.PasteEntity;
import dev.hieplp.pastebin.domain.model.Paste;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "PasteJpaMapperImpl"
)
public interface PasteMapper {

    Paste toModel(PasteEntity entity);

    List<Paste> toModels(List<PasteEntity> entities);

    PasteEntity toEntity(Paste model);

    List<PasteEntity> toEntities(List<Paste> models);

}