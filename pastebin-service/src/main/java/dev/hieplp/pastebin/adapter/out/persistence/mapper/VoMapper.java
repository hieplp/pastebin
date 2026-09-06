package dev.hieplp.pastebin.adapter.out.persistence.mapper;

import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.PasteId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "VoPersistenceMapperImpl"
)
public interface VoMapper {

    default PasteId stringToPasteId(String value) {
        return value == null ? null : PasteId.of(value);
    }

    default String pasteIdToString(PasteId value) {
        return value == null ? null : value.value();
    }

    default Actor stringToActor(String value) {
        return value == null ? null : Actor.of(value);
    }

    default String actorToString(Actor value) {
        return value == null ? null : value.id();
    }

}