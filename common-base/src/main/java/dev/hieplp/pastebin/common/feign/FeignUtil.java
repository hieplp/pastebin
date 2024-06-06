package dev.hieplp.pastebin.common.feign;


import dev.hieplp.pastebin.common.enums.statuscode.ErrorCode;
import dev.hieplp.pastebin.common.enums.statuscode.SuccessCode;
import dev.hieplp.pastebin.common.exception.*;
import dev.hieplp.pastebin.common.payload.response.CommonResponse;

public class FeignUtil {
    public static <T> T parseResponse(CommonResponse<T> response) {
        if (SuccessCode.SUCCESS.getCode().equals(response.code())) {
            return response.data();
        }

        if (ErrorCode.BAD_REQUEST.getCode().equals(response.code())) {
            throw new BadRequestException(response.message());
        }

        if (ErrorCode.NOT_FOUND.getCode().equals(response.code())) {
            throw new NotFoundException(response.message());
        }

        if (ErrorCode.UNAUTHORIZED.getCode().equals(response.code())) {
            throw new UnauthorizedException(response.message());
        }

        if (ErrorCode.FORBIDDEN.getCode().equals(response.code())) {
            throw new AccessDeniedException(response.message());
        }

        throw new UnknownException(response.message());
    }
}
