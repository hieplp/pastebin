package dev.hieplp.pastebin.application.port.in.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeletePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeletePasteResult;
import dev.hieplp.pastebin.domain.vo.PasteId;

public interface DeletePasteUseCase {

    DeletePasteResult delete(DeletePasteCommand command);

    default DeletePasteResult delete(PasteId pasteId) {
        return delete(new DeletePasteCommand(pasteId));
    }

    default DeletePasteResult delete(String pasteId) {
        return delete(pasteId != null && !pasteId.isBlank() ? PasteId.of(pasteId.trim()) : null);
    }

}
