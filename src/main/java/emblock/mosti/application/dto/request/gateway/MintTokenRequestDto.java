package emblock.mosti.application.dto.request.gateway;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MintTokenRequestDto (

        @NotEmpty
        String userId,
        @NotNull
        long tokenId,
        @NotEmpty
        String deletedOn
) { }
