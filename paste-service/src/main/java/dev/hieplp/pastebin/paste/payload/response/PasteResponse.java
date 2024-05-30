package dev.hieplp.pastebin.paste.payload.response;

import dev.hieplp.pastebin.paste.entity.PasteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteResponse {
    private String pasteId;
    private String alias;
    private String description;
    private String content;
    private String ownerId;

    public PasteResponse(PasteEntity entity) {
        this.pasteId = entity.getPasteId();
        this.alias = entity.getAlias();
        this.description = entity.getDescription();
        this.content = entity.getContent();
        this.ownerId = entity.getOwnerId();
    }
}
