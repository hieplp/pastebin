package dev.hieplp.pastebin.paste.payload.response;

import dev.hieplp.pastebin.paste.entity.PasteEntity;

public class UpdatePasteResponse extends PasteResponse {
    public UpdatePasteResponse(PasteEntity entity) {
        super(entity);
    }
}
