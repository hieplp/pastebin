package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.PasteFileMapper;
import dev.hieplp.pastebin.adapter.in.web.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.PasteResponse;
import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.paste.query.GetPasteQuery;
import dev.hieplp.pastebin.application.port.in.paste.CreatePasteUseCase;
import dev.hieplp.pastebin.application.port.in.paste.GetPasteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/pastes")
@RequiredArgsConstructor
public class PasteController {

    private final PasteMapper pasteMapper;
    private final PasteFileMapper pasteFileMapper;

    private final CreatePasteUseCase createPasteUseCase;
    private final GetPasteUseCase getPasteUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<CreatePasteResponse> create(
            @RequestPart("request") @Valid CreatePasteRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        var fileCommands = pasteFileMapper.toCommands(files);
        var result = createPasteUseCase.create(CommandEnvelope.anonymous(
                pasteMapper.toCommand(request, fileCommands)
        ));
        return BaseResponse.ok(pasteMapper.toResponse(result));
    }

    @GetMapping("/{id}")
    public BaseResponse<PasteResponse> get(@PathVariable("id") String id) {
        var result = getPasteUseCase.get(new GetPasteQuery(id));
        return BaseResponse.ok(pasteMapper.toResponse(result));
    }

}
