package dev.hieplp.pastebin.paste.controller;

import dev.hieplp.pastebin.common.auth.UserInfoDetails;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;
import dev.hieplp.pastebin.paste.payload.request.CreatePasteRequest;
import dev.hieplp.pastebin.paste.payload.request.UpdatePasteRequest;
import dev.hieplp.pastebin.paste.payload.response.CreatePasteResponse;
import dev.hieplp.pastebin.paste.payload.response.PasteResponse;
import dev.hieplp.pastebin.paste.payload.response.UpdatePasteResponse;
import dev.hieplp.pastebin.paste.service.PasteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pastes")
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    @PostMapping
    public CommonResponse<CreatePasteResponse> create(@RequestBody CreatePasteRequest request,
                                                      @AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Create paste with request: {}", request);
        var response = pasteService.create(request, userDetails.getUserId());
        return CommonResponse.success(response);
    }

    @PatchMapping("/{pasteId}")
    public CommonResponse<UpdatePasteResponse> update(@PathVariable String pasteId,
                                                      @RequestBody UpdatePasteRequest request,
                                                      @AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Update paste with request: {}", request);
        var response = pasteService.update(pasteId, request, userDetails.getUserId());
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{pasteId}")
    public CommonResponse<?> delete(@PathVariable String pasteId,
                                    @AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Delete paste with pasteId: {}", pasteId);
        pasteService.delete(pasteId, userDetails.getUserId());
        return CommonResponse.success(null);
    }

    @GetMapping("/{pasteId}")
    public CommonResponse<PasteResponse> get(@PathVariable String pasteId,
                                             @AuthenticationPrincipal UserInfoDetails userDetails) {
        log.debug("Get paste with pasteId: {}", pasteId);
        var response = pasteService.get(pasteId, userDetails.getUserId());
        return CommonResponse.success(response);
    }
}
