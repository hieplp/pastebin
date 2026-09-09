package dev.hieplp.pastebin.adapter.out.persistence.mapper;

import dev.hieplp.pastebin.adapter.out.persistence.entity.RootEntity;
import dev.hieplp.pastebin.domain.model.Root;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "RootPersistenceMapperImpl"
)
public interface RootMapper {

    Root toModel(RootEntity entity);

    RootEntity toEntity(Root model);

}
