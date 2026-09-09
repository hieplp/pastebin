package dev.hieplp.pastebin.adapter.in.web.controller;

import dev.hieplp.pastebin.adapter.in.web.mapper.AuthMapper;
import dev.hieplp.pastebin.adapter.in.web.mapper.RootMapper;
import dev.hieplp.pastebin.adapter.in.web.payload.auth.LoginResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.common.BaseResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.root.CreateRootRequest;
import dev.hieplp.pastebin.adapter.in.web.payload.root.CreateRootResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.root.ExistRootResponse;
import dev.hieplp.pastebin.adapter.in.web.payload.root.LoginRootRequest;
import dev.hieplp.pastebin.adapter.in.web.security.AuthCookieWriter;
import dev.hieplp.pastebin.application.dto.common.command.CommandEnvelope;
import dev.hieplp.pastebin.application.port.in.root.CreateRootUseCase;
import dev.hieplp.pastebin.application.port.in.root.ExistRootUseCase;
import dev.hieplp.pastebin.application.port.in.root.LoginRootUseCase;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/roots")
@RequiredArgsConstructor
public class RootController {

    private final RootMapper rootMapper;
    private final AuthMapper authMapper;

    private final AuthCookieWriter authCookieWriter;

    private final CreateRootUseCase createRootUseCase;
    private final ExistRootUseCase existRootUseCase;
    private final LoginRootUseCase loginRootUseCase;

    @PostMapping
    public BaseResponse<CreateRootResponse> create(@Valid @RequestBody CreateRootRequest request) {
        var result = createRootUseCase.create(CommandEnvelope.system(
                rootMapper.toCommand(request)
        ));
        return BaseResponse.ok(rootMapper.toResponse(result));
    }

    @GetMapping("/exists")
    public BaseResponse<ExistRootResponse> exists() {
        return BaseResponse.ok(new ExistRootResponse(existRootUseCase.exists()));
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(
            @Valid @RequestBody LoginRootRequest request,
            HttpServletResponse response
    ) {
        var result = loginRootUseCase.login(CommandEnvelope.anonymous(
                rootMapper.toCommand(request)
        ));
        authCookieWriter.write(response, result);
        return BaseResponse.ok(authMapper.toResponse(result));
    }

}
