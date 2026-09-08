package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.file.FileResponse;
import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.port.in.file.DownloadFileUseCase;
import dev.hieplp.pastebin.application.port.in.file.GetFileByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class PasteFileController {

    private final PasteFileMapper pasteFileMapper;

    private final GetFileByIdUseCase getFileByIdUseCase;
    private final DownloadFileUseCase downloadFileUseCase;

    @GetMapping("/{fileId}")
    public BaseResponse<FileResponse> getFile(
            @PathVariable("fileId") String fileId
    ) {
        var result = getFileByIdUseCase.get(new GetFileByIdQuery(fileId));
        return BaseResponse.ok(pasteFileMapper.toResponse(result));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> download(
            @PathVariable("fileId") String fileId
    ) {
        var result = downloadFileUseCase.download(new GetFileByIdQuery(fileId));
        var mediaType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            if (result.contentType() != null && !result.contentType().isBlank()) {
                mediaType = MediaType.parseMediaType(result.contentType());
            }
        } catch (InvalidMediaTypeException ignored) {
            // ponytail: unknown type → octet-stream
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(result.name(), StandardCharsets.UTF_8)
                        .build()
                        .toString())
                .contentType(mediaType)
                .body(result.content());
    }

}
