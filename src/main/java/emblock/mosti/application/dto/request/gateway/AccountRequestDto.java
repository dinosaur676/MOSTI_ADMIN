package emblock.mosti.application.dto.request.gateway;

import javax.validation.constraints.NotEmpty;

public record AccountRequestDto (
        @NotEmpty
        long userId
){
}
