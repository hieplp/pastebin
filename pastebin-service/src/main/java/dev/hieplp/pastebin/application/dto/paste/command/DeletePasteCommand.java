package dev.hieplp.pastebin.application.dto.paste.command;

import dev.hieplp.pastebin.domain.vo.PasteId;

public record DeletePasteCommand(
        PasteId pasteId
) {

    public static DeletePasteCommand of(PasteId pasteId) {
        return new DeletePasteCommand(pasteId);
    }

    public static DeletePasteCommand of(String pasteId) {
        return new DeletePasteCommand(pasteId != null ? PasteId.of(pasteId) : null);
    }

}
