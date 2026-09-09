package dev.hieplp.pastebin.application.dto.root.result;

import dev.hieplp.pastebin.domain.model.Root;

public record CreateRootResult(
        String rootId,
        String username
) {

    public static CreateRootResult from(Root root) {
        return new CreateRootResult(
                root.getRootId().value(),
                root.getUsername().value()
        );
    }

}
