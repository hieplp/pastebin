package dev.hieplp.pastebin.adapter.out.jpa.mapper;

import dev.hieplp.pastebin.adapter.out.jpa.entity.RootEntity;
import dev.hieplp.pastebin.domain.model.Root;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = VoMapper.class,
        implementationName = "RootJpaMapperImpl"
)
public interface RootMapper {

    Root toModel(RootEntity entity);

    RootEntity toEntity(Root model);

}
