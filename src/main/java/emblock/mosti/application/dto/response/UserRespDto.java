package emblock.mosti.application.dto.response;

import emblock.mosti.application.domain.User;

import java.time.LocalDateTime;

public record UserRespDto(
        String userId,
        String loginId,
        String userName,
        String email,
        String address,
        String phone,
        String cellPhone,
        String school,
        User.UserType type,
        User.UserStatus status,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
) {

    public static UserRespDto 생성(User user){
        return new UserRespDto(
                String.valueOf(user.getUserId()),
                user.getLoginId(),
                user.getUserName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getCellPhone(),
                user.getSchool(),
                user.getType(),
                user.getStatus(),
                user.getCreatedOn(),
                user.getUpdatedOn()
        );
    }
}
