package emblock.mosti.application.dto.request.gateway;

import javax.validation.constraints.NotEmpty;

public record BurnTokenRequestDto(
        String tokenOwner,
        @NotEmpty
        String toAddress,
        @NotEmpty
        long tokenId
) {
}
