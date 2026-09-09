package dev.hieplp.pastebin.application.dto.common.command;

import dev.hieplp.pastebin.domain.vo.Actor;

public record CommandEnvelope<C>(
        C command,
        Actor actor
) {

    public static <C> CommandEnvelope<C> of(
            C command,
            Actor actor
    ) {
        return new CommandEnvelope<>(command, actor);
    }

    public static <C> CommandEnvelope<C> anonymous(C command) {
        return of(command, Actor.anonymous());
    }

    public static <C> CommandEnvelope<C> system(C command) {
        return of(command, Actor.system());
    }


    public <N> CommandEnvelope<N> withCommand(N newCommand) {
        return of(newCommand, actor);
    }

}
