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
        User.UserType type,
        User.UserStatus status,
        String walletId,
        String publicAddress,
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
                user.getType(),
                user.getStatus(),
                user.getWalletId() == null ? "" : user.getWalletId(),
                user.getPublicAddress() == null ? "" : user.getPublicAddress(),
                user.getCreatedOn(),
                user.getUpdatedOn()
        );
    }
}
