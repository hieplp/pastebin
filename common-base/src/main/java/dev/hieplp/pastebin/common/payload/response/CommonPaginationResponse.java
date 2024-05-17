package dev.hieplp.pastebin.common.payload.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class CommonPaginationResponse<T> {

    private List<T> list;

    private Long total;

    public CommonPaginationResponse(Page<?> page, List<T> list) {
        this.list = list;
        this.total = page.getTotalElements();
    }
}
