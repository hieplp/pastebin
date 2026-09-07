package dev.hieplp.pastebin.adapter.in.web.payload.paste;

public record GetFileResponse(
        String fileId,
        String name,
        long size,
        String content
) {
}
