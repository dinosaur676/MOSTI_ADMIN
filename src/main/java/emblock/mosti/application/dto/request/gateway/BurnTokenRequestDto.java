package emblock.mosti.application.dto.request.gateway;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record BurnTokenRequestDto(
        @NotEmpty
        String userId,

        @NotNull
        long tokenId
) {
}
