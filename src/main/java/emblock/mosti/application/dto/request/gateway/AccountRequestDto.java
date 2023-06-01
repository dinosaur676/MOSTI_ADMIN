package emblock.mosti.application.dto.request.gateway;

import jakarta.validation.constraints.NotEmpty;

public record AccountRequestDto (
        @NotEmpty
        long userId
){
}
