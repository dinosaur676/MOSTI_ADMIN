package emblock.mosti.application.dto.request.gateway;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BurnTokenRequestDto(
        @NotEmpty
        String userId,

        @NotNull
        long tokenId
) {
}
