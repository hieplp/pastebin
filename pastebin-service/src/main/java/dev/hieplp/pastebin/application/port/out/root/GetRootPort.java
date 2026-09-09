package dev.hieplp.pastebin.application.port.out.root;

import dev.hieplp.pastebin.domain.model.Root;
import dev.hieplp.pastebin.domain.vo.Username;

import java.util.Optional;

public interface GetRootPort {

    Optional<Root> findByUsername(Username username);

}
