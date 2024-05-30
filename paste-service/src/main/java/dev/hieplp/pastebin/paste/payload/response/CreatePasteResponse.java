package dev.hieplp.pastebin.paste.payload.response;

import dev.hieplp.pastebin.paste.entity.PasteEntity;

public class CreatePasteResponse extends PasteResponse {
    public CreatePasteResponse(PasteEntity entity) {
        super(entity);
    }
}
