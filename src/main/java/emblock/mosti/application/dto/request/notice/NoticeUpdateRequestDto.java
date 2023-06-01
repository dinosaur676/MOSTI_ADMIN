package emblock.mosti.application.dto.request.notice;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NoticeUpdateRequestDto(
        @NotNull
        long id,
        @NotEmpty
        String title,
        @NotEmpty
        String content
) {
}
