package emblock.mosti.application.dto.response;

import emblock.mosti.application.domain.School;

import java.time.LocalDateTime;

public record SchoolRespDto(
        String schoolName,
        long tokenId,
        LocalDateTime createdOn
) {
    public static SchoolRespDto 실행(School school) {
        return new SchoolRespDto(school.getSchoolName(), school.getSchoolTokenId(), school.getCreatedOn());
    }
}
