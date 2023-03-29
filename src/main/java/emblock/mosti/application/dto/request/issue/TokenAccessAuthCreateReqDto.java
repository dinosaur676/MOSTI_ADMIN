package emblock.mosti.application.dto.request.issue;

import javax.validation.constraints.NotEmpty;

public record TokenAccessAuthCreateReqDto (
    @NotEmpty(message = "인증코드는 필수값입니다.")
    String authKey
){}
