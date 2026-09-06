package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteResponse;
import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.port.in.paste.CreatePasteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/pastes")
@RequiredArgsConstructor
public class PasteController {

    private final PasteMapper pasteMapper;

    private final CreatePasteUseCase createPasteUseCase;

    @PostMapping
    public BaseResponse<CreatePasteResponse> create(@Valid @RequestBody CreatePasteRequest request) {
        var result = createPasteUseCase.create(CommandEnvelope.anonymous(
                pasteMapper.toCommand(request)
        ));
        return BaseResponse.ok(pasteMapper.toResponse(result));
    }

}
