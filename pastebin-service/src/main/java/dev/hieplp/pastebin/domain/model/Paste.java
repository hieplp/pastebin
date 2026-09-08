package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.vo.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Paste extends Auditable {

    private PasteId pasteId;
    private Title title;
    private Alias alias;
    private Content content;
    private Privacy privacy;
    private Syntax syntax;
    private Instant expiredAt;
    private boolean burnAfterRead;
    private PasteStatus status;

    public static Paste create(
            Title title,
            Alias alias,
            Content content,
            Privacy privacy,
            Syntax syntax,
            Instant expiredAt,
            boolean burnAfterRead,
            Actor actor
    ) {
        var paste = new Paste();
        var pasteId = PasteId.uuid();

        paste.setPasteId(pasteId);
        paste.setTitle(title);
        paste.setAlias(alias);
        paste.setContent(content);
        paste.setPrivacy(privacy != null ? privacy : Privacy.PUBLIC);
        paste.setSyntax(syntax);
        paste.setExpiredAt(expiredAt);
        paste.setBurnAfterRead(burnAfterRead);
        paste.setStatus(PasteStatus.ACTIVE);
        paste.setCreator(actor);

        return paste;
    }

    public boolean isActive() {
        return status == PasteStatus.ACTIVE;
    }

    public boolean isExpired() {
        return expiredAt != null && expiredAt.isBefore(Instant.now());
    }

    /**
     * Read-only accessibility: not expired AND (active OR burn-after-read).
     * Burned pastes stay readable so tab fetches after GET /pastes/{id} still work.
     */
    public boolean isAccessible() {
        return !isExpired() && (isActive() || burnAfterRead);
    }

    public void deactivate() {
        this.status = PasteStatus.INACTIVE;
    }

}
