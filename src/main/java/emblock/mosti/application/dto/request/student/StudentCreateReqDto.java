package emblock.mosti.application.dto.request.student;

import javax.validation.constraints.NotEmpty;

public record StudentCreateReqDto (
    @NotEmpty
    String userId,

    @NotEmpty(message = "이름은 필수값입니다.")
    String userName,
    @NotEmpty(message = "학번은 필수값입니다.")
    String studentId,
    @NotEmpty(message = "학교는 필수값입니다.")
    String school,
    @NotEmpty(message = "전공은 필수값입니다.")
    String major
) {}