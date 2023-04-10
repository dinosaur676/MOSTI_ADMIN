package emblock.mosti.application.dto.request.gateway;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public record MintTokenRequestDto (
        @NotEmpty
        String tokenOwner,
        @NotEmpty
        String toAddress,
        @NotEmpty
        long tokenId,

        LocalDateTime deletedOn
) { }
