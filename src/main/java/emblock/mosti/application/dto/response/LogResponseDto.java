package emblock.mosti.application.dto.response;

import emblock.mosti.application.domain.Log;
import emblock.mosti.application.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LogResponseDto (
        long userId,
        String userName,
        String loginId,
        String label,

        User.UserType type,
        User.UserStatus status,

        LocalDateTime createdOn
){
    public static LogResponseDto 실행(Log log) {
        return new LogResponseDto(
                log.getUserId(),
                log.getUserName(),
                log.getLoginId(),
                log.getLabel(),
                log.getUserType(),
                log.getUserStatus(),
                log.getCreatedOn()
        );
    }
}
