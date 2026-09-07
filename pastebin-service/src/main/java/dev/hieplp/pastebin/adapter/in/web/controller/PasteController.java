package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.PasteMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.CreatePasteResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.paste.GetPasteResponse;
import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.dto.file.command.CreateFileCommand;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/pastes")
@RequiredArgsConstructor
public class PasteController {

    private final PasteMapper pasteMapper;

    private final CreatePasteUseCase createPasteUseCase;
    private final GetPasteUseCase getPasteUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<CreatePasteResponse> create(
            @RequestPart("request") @Valid CreatePasteRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        var fileCommands = buildFileCommands(files);
        var result = createPasteUseCase.create(CommandEnvelope.anonymous(
                pasteMapper.toCommand(request, fileCommands)
        ));
        return BaseResponse.ok(pasteMapper.toResponse(result));
    }

    @GetMapping("/{id}")
    public BaseResponse<GetPasteResponse> get(@PathVariable("id") String id) {
        var result = getPasteUseCase.get(new GetPasteQuery(id));
        return BaseResponse.ok(pasteMapper.toResponse(result));
    }

    private List<CreateFileCommand> buildFileCommands(List<MultipartFile> files) throws IOException {
        if (files == null) {
            return List.of();
        }

        var fileCommands = new ArrayList<CreateFileCommand>(files.size());
        for (var file : files) {
            fileCommands.add(new CreateFileCommand(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    file.getBytes()
            ));
        }

        return fileCommands;
    }

}
