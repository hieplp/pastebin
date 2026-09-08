package dev.hieplp.pastebin.adapter.in.web.mapper;

import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.Alias;
import dev.hieplp.pastebin.domain.vo.Content;
import dev.hieplp.pastebin.domain.vo.ContentType;
import dev.hieplp.pastebin.domain.vo.FileId;
import dev.hieplp.pastebin.domain.vo.FileName;
import dev.hieplp.pastebin.domain.vo.PasteId;
import dev.hieplp.pastebin.domain.vo.Title;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "VoWebMapperImpl"
)
public interface VoMapper {

    default PasteId stringToPasteId(String value) {
        return value == null ? null : PasteId.of(value);
    }

    default String pasteIdToString(PasteId value) {
        return value == null ? null : value.value();
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
        return Content.of(value);
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

}
