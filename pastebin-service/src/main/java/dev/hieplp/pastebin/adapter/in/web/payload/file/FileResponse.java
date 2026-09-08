package dev.hieplp.pastebin.adapter.in.web.payload.file;

public record FileResponse(
        String fileId,
        String name,
        long size,
        String content
) {
}
