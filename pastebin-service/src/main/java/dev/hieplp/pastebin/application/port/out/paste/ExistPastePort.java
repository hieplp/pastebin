package dev.hieplp.pastebin.application.port.out.paste;

import dev.hieplp.pastebin.domain.vo.Alias;

public interface ExistPastePort {

    boolean existsByAlias(Alias alias);

}
