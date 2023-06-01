package emblock.mosti.application.dto.request.notice;

import jakarta.validation.constraints.NotEmpty;

public record NoticeRequestDto(
        @NotEmpty
        String writer,
        @NotEmpty
        String title,
        @NotEmpty
        String content
) {
}
