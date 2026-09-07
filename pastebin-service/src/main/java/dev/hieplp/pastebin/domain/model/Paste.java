package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.enums.PasteStatus;
import dev.hieplp.pastebin.domain.enums.Privacy;
import dev.hieplp.pastebin.domain.enums.Syntax;
import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.PasteId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paste extends Auditable {

    private PasteId pasteId;
    private String title;
    private String content;
    private Privacy privacy;
    private Syntax syntax;
    private PasteStatus status;

    public static Paste create(
            String title,
            String content,
            Privacy privacy,
            Syntax syntax,
            Actor actor
    ) {
        var paste = new Paste();
        var pasteId = PasteId.uuid();

        paste.setPasteId(pasteId);
        paste.setTitle(title);
        paste.setContent(content);
        paste.setPrivacy(privacy);
        paste.setSyntax(syntax);
        paste.setStatus(PasteStatus.ACTIVE);
        paste.setCreator(actor);

        return paste;
    }

}
