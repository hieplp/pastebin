package dev.hieplp.pastebin.adapter.out.jpa.mapper;

import dev.hieplp.pastebin.adapter.out.jpa.entity.PasteFileEntity;
import dev.hieplp.pastebin.domain.model.PasteFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "PasteFileJpaMapperImpl"
)
public interface PasteFileMapper {

    @Mapping(target = "content", ignore = true)
    PasteFile toModel(PasteFileEntity entity);

    PasteFileEntity toEntity(PasteFile model);

    List<PasteFile> toModels(List<PasteFileEntity> entities);

    List<PasteFileEntity> toEntities(List<PasteFile> models);

}
