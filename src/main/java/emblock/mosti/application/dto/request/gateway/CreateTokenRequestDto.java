package emblock.mosti.application.dto.request.gateway;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTokenRequestDto(
    @NotEmpty(message = "메타데이터는 필수 입니다.")
    String metaData
) {

}
