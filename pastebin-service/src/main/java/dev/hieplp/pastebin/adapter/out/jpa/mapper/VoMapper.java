package dev.hieplp.pastebin.adapter.out.jpa.mapper;

import dev.hieplp.pastebin.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "VoJpaMapperImpl"
)
public interface VoMapper {

    default PasteId stringToPasteId(String value) {
        return value == null ? null : PasteId.of(value);
    }

    default String pasteIdToString(PasteId value) {
        return value == null ? null : value.value();
    }

    default List<String> toPasteIdStrings(List<PasteId> ids) {
        return ids == null ? List.of() : ids.stream().map(this::pasteIdToString).toList();
    }

    default FileId stringToFileId(String value) {
        return value == null ? null : FileId.of(value);
    }

    default String fileIdToString(FileId value) {
        return value == null ? null : value.value();
    }

    default Actor stringToActor(String value) {
        return value == null ? null : Actor.of(value);
    }

    default String actorToString(Actor value) {
        return value == null ? null : value.id();
    }

    default Title stringToTitle(String value) {
        return value == null ? null : Title.of(value);
    }

    default String titleToString(Title value) {
        return value == null ? null : value.value();
    }

    default Content stringToContent(String value) {
        return value == null ? null : Content.of(value);
    }

    default String contentToString(Content value) {
        return value == null ? null : value.value();
    }

    default Alias stringToAlias(String value) {
        return value == null ? null : Alias.of(value);
    }

    default String aliasToString(Alias value) {
        return value == null ? null : value.value();
    }

    default FileName stringToFileName(String value) {
        return value == null ? null : FileName.of(value);
    }

    default String fileNameToString(FileName value) {
        return value == null ? null : value.value();
    }

    default ContentType stringToContentType(String value) {
        return value == null ? null : ContentType.of(value);
    }

    default String contentTypeToString(ContentType value) {
        return value == null ? null : value.value();
    }

    default StorageKey stringToStorageKey(String value) {
        return value == null ? null : StorageKey.of(value);
    }

    default String storageKeyToString(StorageKey value) {
        return value == null ? null : value.value();
    }

    default RootId stringToRootId(String value) {
        return value == null ? null : RootId.of(value);
    }

    default String rootIdToString(RootId value) {
        return value == null ? null : value.value();
    }

    default Username stringToUsername(String value) {
        return value == null ? null : Username.of(value);
    }

    default String usernameToString(Username value) {
        return value == null ? null : value.value();
    }

}