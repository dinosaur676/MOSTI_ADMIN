package emblock.mosti.application.dto.request.gateway;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MintTokenRequestDto (

        @NotEmpty
        String userId,
        @NotNull
        long tokenId,
        @NotEmpty
        String deletedOn
) { }
