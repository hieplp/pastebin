package dev.hieplp.pastebin.application.port.in.file;

import dev.hieplp.pastebin.application.dto.file.command.DeleteFilesCommand;
import dev.hieplp.pastebin.application.dto.file.result.DeleteFilesResult;
import dev.hieplp.pastebin.domain.vo.PasteId;

public interface DeleteFilesUseCase {

    DeleteFilesResult delete(DeleteFilesCommand command);

    default DeleteFilesResult delete(PasteId pasteId) {
        return delete(new DeleteFilesCommand(pasteId));
    }

}
