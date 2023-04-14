package emblock.mosti.application.dto.response;

import emblock.mosti.application.domain.School;

import java.time.LocalDateTime;

public record SchoolRespDto(
        long userId,

        String schoolName,
        long tokenId,
        LocalDateTime createdOn
) {
    public static SchoolRespDto 실행(School school) {
        return new SchoolRespDto(school.getUserId(), school.getSchoolName(), school.getSchoolTokenId(), school.getCreatedOn());
    }
}
