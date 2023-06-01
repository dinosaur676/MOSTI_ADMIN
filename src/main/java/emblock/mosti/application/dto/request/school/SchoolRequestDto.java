package emblock.mosti.application.dto.request.school;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SchoolRequestDto (
        @NotEmpty
        String schoolName,
        @NotNull
        long tokenId
) {
}
