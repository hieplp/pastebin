package dev.hieplp.pastebin.application.port.in.paste;

import dev.hieplp.pastebin.application.dto.paste.command.DeletePasteCommand;
import dev.hieplp.pastebin.application.dto.paste.result.DeletePasteResult;
import dev.hieplp.pastebin.domain.vo.PasteId;
import dev.hieplp.pastebin.domain.util.Strings;

public interface DeletePasteUseCase {

    DeletePasteResult delete(DeletePasteCommand command);

    default DeletePasteResult delete(PasteId pasteId) {
        return delete(new DeletePasteCommand(pasteId));
    }

    default DeletePasteResult delete(String pasteId) {
        return delete(Strings.trim(pasteId).map(PasteId::of).orElse(null));
    }

}
