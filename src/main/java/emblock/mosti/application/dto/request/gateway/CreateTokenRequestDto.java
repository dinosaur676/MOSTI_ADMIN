package emblock.mosti.application.dto.request.gateway;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTokenRequestDto(
    @NotNull(message = "토큰 타입은 필수 입니다.")
    long tokenType,

    @NotEmpty(message = "메타데이터는 필수 입니다.")
    String metaData
) {

}
