package dev.hieplp.pastebin.domain.model;

import dev.hieplp.pastebin.domain.vo.Actor;
import dev.hieplp.pastebin.domain.vo.RootId;
import dev.hieplp.pastebin.domain.vo.Username;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Root extends Auditable {

    private RootId rootId;
    private Username username;
    private String passwordHash;
    private String email;

    public static Root create(Username username, String passwordHash, Actor actor) {
        var root = new Root();
        var rootId = RootId.uuid();

        root.setRootId(rootId);
        root.setUsername(username);
        root.setPasswordHash(passwordHash);
        root.setCreator(actor);

        return root;
    }

}
