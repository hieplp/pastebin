package dev.hieplp.pastebin.common.payload.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class CommonPaginationRequest {
    private Integer pageNo;
    private Integer pageSize;

    public Pageable toPageable() {
        return PageRequest.of(pageNo, pageSize);
    }
}
