package dev.hieplp.pastebin.application.port.in.paste;

import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.dto.paste.result.GetPasteResult;

public interface GetPasteUseCase {

    GetPasteResult get(GetPasteQuery query);

}
