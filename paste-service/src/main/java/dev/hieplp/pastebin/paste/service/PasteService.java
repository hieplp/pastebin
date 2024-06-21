package dev.hieplp.pastebin.paste.service;

import dev.hieplp.pastebin.common.payload.response.CommonPaginationResponse;
import dev.hieplp.pastebin.paste.payload.request.CreatePasteRequest;
import dev.hieplp.pastebin.paste.payload.request.GetOwnPastesRequest;
import dev.hieplp.pastebin.paste.payload.request.UpdatePasteRequest;
import dev.hieplp.pastebin.paste.payload.response.CreatePasteResponse;
import dev.hieplp.pastebin.paste.payload.response.PasteResponse;
import dev.hieplp.pastebin.paste.payload.response.UpdatePasteResponse;

public interface PasteService {
    /**
     * Create paste
     *
     * @param request Create paste request
     * @param ownerId Owner id
     * @return Create paste response
     * @throws dev.hieplp.pastebin.common.exception.DuplicateException If alias is already taken
     */
    CreatePasteResponse create(CreatePasteRequest request, String ownerId);

    /**
     * Update paste
     *
     * @param pasteId   Paste id
     * @param request   Update paste request
     * @param updatedBy ID of user who updated the paste
     * @return Update paste response
     * @throws dev.hieplp.pastebin.common.exception.AccessDeniedException If user is not the owner of paste
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException     If paste not found
     * @throws dev.hieplp.pastebin.common.exception.DuplicateException    If alias is already taken
     */
    UpdatePasteResponse update(String pasteId, UpdatePasteRequest request, String updatedBy);

    /**
     * Delete paste
     *
     * @param pasteId   Paste id
     * @param deletedBy ID of user who deleted the paste
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException     If paste not found
     * @throws dev.hieplp.pastebin.common.exception.AccessDeniedException If user is not the owner of paste
     */
    void delete(String pasteId, String deletedBy);

    /**
     * Get paste
     *
     * @param pasteId Paste id
     * @param userId  User id
     * @return Paste response
     * @throws dev.hieplp.pastebin.common.exception.NotFoundException     If paste not found
     * @throws dev.hieplp.pastebin.common.exception.AccessDeniedException If user is not the owner of paste
     */
    PasteResponse get(String pasteId, String userId);

    /**
     * Get public paste by alias
     *
     * @param username Username
     * @param alias    Alias
     * @return Paste response
     */
    PasteResponse getByUsernameAndAlias(String username, String alias);

    /**
     * Get own pastes
     *
     * @param request Get own pastes request
     * @param ownerId Owner id
     * @return Common pagination response of paste response
     */
    CommonPaginationResponse<PasteResponse> getOwnPastes(GetOwnPastesRequest request, String ownerId);
}
