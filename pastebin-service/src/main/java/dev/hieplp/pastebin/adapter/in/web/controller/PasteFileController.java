package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.file.FileResponse;
import dev.hieplp.pastebin.application.dto.file.query.GetFileByIdQuery;
import dev.hieplp.pastebin.application.port.in.file.GetFileByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class PasteFileController {

    private final PasteFileMapper pasteFileMapper;

    private final GetFileByIdUseCase getFileByIdUseCase;

    @GetMapping("/{fileId}")
    public BaseResponse<FileResponse> getFile(
            @PathVariable("fileId") String fileId
    ) {
        var result = getFileByIdUseCase.get(new GetFileByIdQuery(fileId));
        return BaseResponse.ok(pasteFileMapper.toResponse(result));
    }

}
